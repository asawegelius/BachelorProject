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
import se.wegelius.speedvoter.voteservice.dao.VotesDao;
import se.wegelius.speedvoter.voteservice.model.Votes;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.ElectionsHO;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OptionsHO;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.VotesHO;

/**
 *
 * @author asawe
 */
@Path("/votes")
public class VotesEndpoint {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VotesEndpoint.class);
    //@Context
    private static VotesDao dao;
    private static OptionsDao optionsDao;
    private static ElectionsDao electionsDao;

    public VotesEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<VotesHO> set = dao.getAll();
        Set<Votes> events = new HashSet<>();
        for (VotesHO ho : set) {
            events.add(new Votes(ho.getElections().getElectionId(), ho.getOptions().getOptionId()));
        }
        return Response.ok(toJson(events), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        VotesHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Votes(ho.getElections().getElectionId(), ho.getOptions().getOptionId())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{election_id}/{option_id}")
    public Response createJson(
            @Min(value=0, message = "The election id should not be less that 0. ")      
            @NotNull(message = "You need to state the election id. ")
            @PathParam("election_id") Integer election_id,
            @Min(value=0, message = "The option id should not be less that 0. ")      
            @NotNull(message = "You need to state the option id. ")
            @PathParam("option_id") Integer option_id) {
        checkContext();
        // Require valid election id to create.
        ElectionsHO elho = electionsDao.findByID(election_id);
        if (elho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There is no election with id" + election_id + ".\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Require valid option id to create.
        OptionsHO oho = optionsDao.findByID(option_id);
        if (oho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There is no option with id" + option_id + ".\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Votes and add it to the database.
        VotesHO ho = new VotesHO();
        ho.setElections(elho);
        ho.setOptions(oho);
        dao.save(ho);
        return Response.ok(toJson(new Votes(ho.getVoteId(), ho.getElections().getElectionId(), ho.getOptions().getOptionId())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update/{id: \\d+}/{election_id}/{option_id}")
    public Response updateJson(
            @Min(value=0, message = "The vote id should not be less that 0. ")      
            @NotNull(message = "You need to state the vote id. ")
            @PathParam("id") Integer id,
            @Min(value=0, message = "The election id should not be less that 0. ")      
            @NotNull(message = "You need to state the election id. ")
            @PathParam("election_id") Integer election_id,
            @Min(value=0, message = "The option id should not be less that 0. ")      
            @NotNull(message = "You need to state the option id. ")
            @PathParam("option_id") Integer option_id) {
        checkContext();    
        // Require valid election id to update.
        ElectionsHO elho = electionsDao.findByID(election_id);
        if (elho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There is no election with id" + election_id + ".\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Require valid option id to update.
        OptionsHO oho = optionsDao.findByID(option_id);
        if (oho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There is no option with id" + option_id + ".\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Require valid vote id to update
        VotesHO ho = dao.findByID(id);
        if (ho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There is no vote with id" + id + ".\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
            // Update.
            ho.setElections(elho);
            ho.setOptions(oho);
            dao.update(ho);
            return Response.ok(toJson(new Votes(ho.getVoteId(), ho.getElections().getElectionId(), ho.getOptions().getOptionId())), MediaType.APPLICATION_JSON).build();
        
    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg;
        VotesHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no vote with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Vote " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new VotesDao();
            optionsDao = new OptionsDao();
            electionsDao = new ElectionsDao();
        }
    }

    // Votes --> JSON document
    private String toJson(Votes vote) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(vote);
        } catch (Exception e) {
        }
        return json;
    }

    // Votes set --> JSON document
    private String toJson(Set<Votes> votesSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(votesSet);
        } catch (Exception e) {
        }
        return json;
    }

}
