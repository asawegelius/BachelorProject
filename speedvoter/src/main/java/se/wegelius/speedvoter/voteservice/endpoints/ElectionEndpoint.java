/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
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
import se.wegelius.speedvoter.voteservice.dao.EventsDao;
import se.wegelius.speedvoter.voteservice.model.Elections;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.ElectionsHO;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.EventsHO;

/**
 *
 * @author asawe
 */
@Path("/elections")
public class ElectionEndpoint {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ElectionEndpoint.class);
    //@Context
    private static ElectionsDao dao;
    private static EventsDao eventsDao;

    public ElectionEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<ElectionsHO> set = dao.getAll();
        Set<Elections> elections = new HashSet<>();
        for (ElectionsHO ho : set) {
            elections.add(new Elections(ho.getElectionId(), ho.getEvents().getEventId(), ho.getPost(),
                    ho.getDate(), ho.getMinVotes(), ho.getMaxVotes(), ho.getSecret(), ho.getActive()));
        }
        return Response.ok(toJson(elections), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        ElectionsHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Elections(ho.getElectionId(), ho.getEvents().getEventId(), ho.getPost(),
                    ho.getDate(), ho.getMinVotes(), ho.getMaxVotes(), ho.getSecret(), ho.getActive())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{event_id}/{post}/{date}/{min_votes}/{max_votes}/{secret}/{active}")
    public Response createJson(
            @Min(value=0, message = "The event id should not be less that 0. ")      
            @NotNull(message = "You need to state the event id. ")
            @PathParam("event_id") Integer event_id,
            @Size(min = 2, max = 45, message = "The post Length should be "
	    		+ "between 2 and 45 character. ")           
            @NotNull(message = "You need to state the post. ")
            @PathParam("post") String post,     
            @NotNull(message = "You need to state the date. ")
            @PathParam("date") Date date,
            @Min(value=0, message = "The min_votes should not be less that 0. ")
            @NotNull(message = "You need to state min votes. ")
            @PathParam("min_votes") Integer min_votes,
            @Min(value=0, message = "max_votes should not be less that 0")
            @NotNull(message = "You need to state max votes. ")
            @PathParam("max_votes") Integer max_votes,
            @NotNull(message = "secret should be true or false")
            @PathParam("secret") boolean secret,
            @NotNull(message = "active should be true or false")
            @PathParam("active") String active) throws ValidationException {
        checkContext();
        // Required parameters to create.

        EventsHO event = eventsDao.findByID(event_id);
        if (event == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There are no event with id " + event_id).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // Otherwise, create the Election and add it to the database.
        ElectionsHO ho = new ElectionsHO();
        ho.setPost(post);
        dao.save(ho);
        return Response.ok(toJson(new Elections(ho.getElectionId(), ho.getEvents().getEventId(), ho.getPost(),
                    ho.getDate(), ho.getMinVotes(), ho.getMaxVotes(), ho.getSecret(), ho.getActive())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("update/{election_id: \\d+}/{event_id}/{post}/{date}/{min_votes}/{max_votes}/{secret}/{active}")
    public Response updateJson(
            @Min(value=0, message = "The election id should not be less that 0. ")      
            @NotNull(message = "You need to state the election id. ")
            @PathParam("election_id") Integer election_id,
            @Min(value=0, message = "The event id should not be less that 0. ")      
            @NotNull(message = "You need to state the event id. ")
            @PathParam("event_id") Integer event_id,
            @Size(min = 2, max = 45, message = "The post Length should be "
	    		+ "between 2 and 45 character. ")           
            @NotNull(message = "You need to state the post. ")
            @PathParam("post") String post,     
            @NotNull(message = "You need to state the date. ")
            @PathParam("date") Date date,
            @Min(value=0, message = "The min_votes should not be less that 0. ")
            @NotNull(message = "You need to state min votes. ")
            @PathParam("min_votes") Integer min_votes,
            @Min(value=0, message = "max_votes should not be less that 0")
            @NotNull(message = "You need to state max votes. ")
            @PathParam("max_votes") Integer max_votes,
            @NotNull(message = "secret should be true or false")
            @PathParam("secret") boolean secret,
            @NotNull(message = "active should be true or false")
            @PathParam("active") boolean active) throws ValidationException {
        checkContext();
        ElectionsHO ho = dao.findByID(election_id);
        EventsHO evho = eventsDao.findByID(event_id);
        // Check that sufficient data are present to do an edit.
        String msg;
        if (ho == null) {
            msg = "There is no election with id " + election_id + "\n";
        } else if (evho == null) {
            msg = "There is no event with id " + event_id + "\n";
        }else {
            // Update.
            ho.setEvents(evho);
            ho.setPost(post);
            ho.setDate(date);
            ho.setMinVotes(min_votes);
            ho.setMaxVotes(max_votes);
            ho.setSecret(secret);
            ho.setActive(active);
            dao.update(ho);
            return Response.ok(toJson(new Elections(ho.getElectionId(), ho.getEvents().getEventId(), ho.getPost(),
                    ho.getDate(), ho.getMinVotes(), ho.getMaxVotes(), ho.getSecret(), ho.getActive())), MediaType.APPLICATION_JSON).build();
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
        ElectionsHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no election with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Election " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new ElectionsDao();
            eventsDao = new EventsDao();
        }
    }

    // Elections --> JSON document
    private String toJson(Elections election) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(election);
        } catch (Exception e) {
        }
        return json;
    }

    // Elections set --> JSON document
    private String toJson(Set<Elections> electionSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(electionSet);
        } catch (Exception e) {
        }
        return json;
    }

}
