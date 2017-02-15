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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import se.wegelius.speedvoter.voteservice.dao.OrgsDao;
import se.wegelius.speedvoter.voteservice.model.Orgs;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OrgsHO;

/**
 *
 * @author asawe
 */
@Path("/orgs")
public class OrgsEndpoint {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrgsEndpoint.class);
    //@Context
    private static OrgsDao dao;

    public OrgsEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<OrgsHO> set = dao.getAll();
        Set<Orgs> orgs = new HashSet<>();
        for (OrgsHO ho : set) {
            orgs.add(new Orgs(ho.getOrgId(), ho.getOrgname()));
        }
        return Response.ok(toJson(orgs), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        OrgsHO org = dao.findByID(id);
        if (org == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Orgs(org.getOrgId(), org.getOrgname())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{name}")
    public Response createJson(@PathParam("org_name") String org_name) {
        checkContext();
        // Require org name to create.
        if (org_name == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("Property 'org_name' is missing.\n").
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Org and add it to the database.
        OrgsHO org = new OrgsHO();
        org.setOrgname(org_name);
        dao.save(org);
        return Response.ok(toJson(new Orgs(org.getOrgId(), org.getOrgname())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update/{id: \\d+}/{name}")
    public Response updateJson(@PathParam("id") int id,
            @PathParam("org_name") String org_name) {
        checkContext();
        OrgsHO org = dao.findByID(id);
        // Check that sufficient data are present to do an edit.
        String msg;
        if (org == null) {
            msg = "There is no org with id " + id + "\n";
        } else if (org_name == null) {
            msg = "org_name is not given: nothing to edit\n";
        } else {
            // Update.
            org.setOrgname(org_name);
            dao.update(org);
            return Response.ok(toJson(new Orgs(org.getOrgId(), org.getOrgname())), MediaType.APPLICATION_JSON).build();
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
        OrgsHO org = dao.findByID(id);
        if (org == null) {
            msg = "There is no org with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(org);
        msg = "Org " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new OrgsDao();
        }
    }

    // Orgs --> JSON document
    private String toJson(Orgs org) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(org);
        } catch (Exception e) {
        }
        return json;
    }

    // Orgs set --> JSON document
    private String toJson(Set<Orgs> orgSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(orgSet);
        } catch (Exception e) {
        }
        return json;
    }

}
