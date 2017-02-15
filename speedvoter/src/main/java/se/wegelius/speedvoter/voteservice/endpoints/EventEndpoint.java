/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.dao.EventsDao;
import se.wegelius.speedvoter.voteservice.model.Events;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.EventsHO;

/**
 *
 * @author asawe
 */
@Path("/events")
public class EventEndpoint {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventEndpoint.class);
    //@Context
    private static EventsDao dao;

    public EventEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<EventsHO> set = dao.getAll();
        Set<Events> events = new HashSet<>();
        for (EventsHO ho : set) {
            events.add(new Events(ho.getEventId(), ho.getEventName(), ho.getStartDate(), ho.getEndDate(), ho.getOrgs().getOrgId()));
        }
        return Response.ok(toJson(events), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        EventsHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Events(ho.getEventId(), ho.getEventName(), ho.getStartDate(), ho.getEndDate(), ho.getOrgs().getOrgId())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{org_name}")
    public Response createJson(@QueryParam("event_name") String event_name) {
        checkContext();
        // Require event name to create.
        if (event_name == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Property 'event_name' is missing.\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Events and add it to the database.
        EventsHO ho = new EventsHO();
        ho.setEventName(event_name);
        dao.save(ho);
        return Response.ok(toJson(new Events(ho.getEventId(), ho.getEventName(), ho.getStartDate(), ho.getEndDate(), ho.getOrgs().getOrgId())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update/{id: \\d+}/{org_name}")
    public Response updateJson(@PathParam("id") int id,
            @QueryParam("org_name") String event_name) {
        checkContext();
        EventsHO ho = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg;
        if (ho == null) {
            msg = "There is no org with id " + id + "\n";
        } else if (event_name == null) {
            msg = "org_name is not given: nothing to edit\n";
        } else {
            // Update.
            ho.setEventName(event_name);
            dao.update(ho);
            return Response.ok(toJson(new Events(ho.getEventId(), ho.getEventName(), ho.getStartDate(), ho.getEndDate(), ho.getOrgs().getOrgId())), MediaType.APPLICATION_JSON).build();
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
        EventsHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no event with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Event " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new EventsDao();
        }
    }

    // Events --> JSON document
    private String toJson(Events event) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(event);
        } catch (Exception e) {
        }
        return json;
    }

    // Events set --> JSON document
    private String toJson(Set<Events> eventSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(eventSet);
        } catch (Exception e) {
        }
        return json;
    }

}
