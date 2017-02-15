/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.endpoints;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.ValidationException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.dao.ElectionsDao;
import se.wegelius.speedvoter.voteservice.dao.OptionsDao;
import se.wegelius.speedvoter.voteservice.model.Options;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.ElectionsHO;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OptionsHO;

/**
 *
 * @author asawe
 */
@Path("/options")
public class OptionsEndpoint {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OptionsEndpoint.class);
    //@Context
    private static OptionsDao dao;
    private static ElectionsDao electionsDao;

    public OptionsEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<OptionsHO> set = dao.getAll();
        Set<Options> options = new HashSet<>();
        for (OptionsHO ho : set) {
            options.add(new Options(ho.getElections().getElectionId(), ho.getTheOption()));
        }
        return Response.ok(toJson(options), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        OptionsHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Options(ho.getElections().getElectionId(), ho.getTheOption())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{election_id}/{option}")
    public Response createJson(
            @Min(value=0, message = "The election id should not be less that 0. ")      
            @NotNull(message = "You need to state the election id. ")
            @PathParam("election_id") Integer election_id,
            @Size(min = 2, max = 60, message = "The option Length should be "
	    		+ "between 2 and 60 character. ")           
            @NotNull(message = "You need to state the option. ")
            @PathParam("option") String option ) throws ValidationException {
        checkContext();
        // Required parameters to create.

        ElectionsHO election = electionsDao.findByID(election_id);
        if (election == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There are no election with id " + election_id).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // Otherwise, create the Option and add it to the database.
        OptionsHO ho = new OptionsHO();
        ho.setTheOption(option);
        dao.save(ho);
        return Response.ok(toJson(new Options(ho.getElections().getElectionId(), ho.getTheOption())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("update/{option_id: \\d+}/{election_id}/{option}")
    public Response updateJson(
            @Min(value=0, message = "The option id should not be less that 0. ")      
            @NotNull(message = "You need to state the option id. ")
            @PathParam("option_id") Integer option_id,
            @Min(value=0, message = "The election id should not be less that 0. ")      
            @NotNull(message = "You need to state the election id. ")
            @PathParam("election_id") Integer election_id,
            @Size(min = 2, max = 45, message = "The option length should be "
	    		+ "between 2 and 45 character. ")           
            @NotNull(message = "You need to state the option. ")
            @PathParam("option") String option) throws ValidationException {
        checkContext();
        OptionsHO ho = dao.findByID(option_id);
        ElectionsHO elho = electionsDao.findByID(election_id);
        // Check that sufficient data are present to do an edit.
        String msg;
        if (ho == null) {
            msg = "There is no option with id " + option_id + "\n";
        } else if (elho == null) {
            msg = "There is no election with id " + election_id + "\n";
        }else {
            // Update.
            ho.setElections(elho);
            ho.setTheOption(option);
            dao.update(ho);
            return Response.ok(toJson(new Options(ho.getElections().getElectionId(), ho.getTheOption())), MediaType.APPLICATION_JSON).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg;
        OptionsHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no option with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Option " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new OptionsDao();
            electionsDao = new ElectionsDao();
        }
    }

    // Options --> JSON document
    private String toJson(Options option) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(option);
        } catch (Exception e) {
        }
        return json;
    }

    // Options set --> JSON document
    private String toJson(Set<Options> optionSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(optionSet);
        } catch (Exception e) {
        }
        return json;
    }

}
