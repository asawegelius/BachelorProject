/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.endpoints;

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
import se.wegelius.identityservice.dao.LoginsDao;
import se.wegelius.identityservice.dao.UsersDao;
import se.wegelius.identityservice.model.HO.LoginsHO;
import se.wegelius.identityservice.model.HO.UsersHO;
import se.wegelius.identityservice.model.Logins;

/**
 *
 * @author asawe
 */
@Path("/logins")
public class LoginsEndpoint {
        
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginsEndpoint.class);
    //@Context
    private static LoginsDao dao;
    private static UsersDao usersDao;

    public LoginsEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<LoginsHO> set = dao.getAll();
        Set<Logins> logins = new HashSet<>();
        for (LoginsHO ho : set) {
            logins.add(new Logins(ho.getLoginsId(), ho.getUsers().getEmail(), ho.getPasswordSalt(), ho.getPasswordHash()));
        }
        return Response.ok(toJson(logins), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        LoginsHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Logins(ho.getLoginsId(), ho.getUsers().getEmail(), ho.getPasswordSalt(), ho.getPasswordHash())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{user_id: \\d+}/{password_salt}/{password_hash}")
    public Response createJson(
            @Min(value = 0, message = "The user id should not be less that 0. ")
            @NotNull(message = "You need to state the user id. ")
            @PathParam("user_id") Integer user_id,
            @NotNull(message = "You need to state the password salt. ")
            @PathParam("password_salt") String password_salt,
            @NotNull(message = "You need to state the password_hash. ")
            @PathParam("password_hash") String password_hash) {
        checkContext();
 
        // Otherwise, create the Logins and add it to the database.
        LoginsHO ho = new LoginsHO();
        UsersHO uho = usersDao.findByID(user_id);
        String msg = "";
        if (uho == null) {
            msg += "There is no user with id " + user_id + "\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } 
        ho.setUsers(uho);
        ho.setPasswordSalt(password_salt);
        ho.setPasswordHash(password_hash);
        dao.save(ho);
        return Response.ok(toJson(new Logins(ho.getLoginsId(), ho.getUsers().getEmail(), ho.getPasswordSalt(), ho.getPasswordHash())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update/{logins_id: \\d+}/{user_id: \\d+}/{password_salt}/{password_hash}")
    public Response updateJson(
            @Min(value=0, message = "The logins id should not be less that 0. ")      
            @NotNull(message = "You need to state the logins id. ")
            @PathParam("logins_id") Integer logins_id,
            @Min(value = 0, message = "The user id should not be less that 0. ")
            @NotNull(message = "You need to state the user id. ")
            @PathParam("user_id") Integer user_id,
            @NotNull(message = "You need to state the password salt. ")
            @PathParam("password_salt") String password_salt,
            @NotNull(message = "You need to state the password_hash. ")
            @PathParam("password_hash") String password_hash) {
        checkContext();
        LoginsHO ho = dao.findByID(logins_id);
        UsersHO uho = usersDao.findByID(user_id);
        // Check that sufficient data are present to do an edit.
        String msg = "";
        if (ho == null) {
            msg += "There is no logins with id " + logins_id + "\n";
        } else if (uho == null) {
            msg += "There is no users with id " + user_id + "\n";
        }          
        else {
            // Update.
            ho.setUsers(uho);
            ho.setPasswordSalt(password_salt);
            ho.setPasswordHash(password_hash);
            dao.update(ho);
            return Response.ok(toJson(new Logins(ho.getLoginsId(), ho.getUsers().getEmail(), ho.getPasswordSalt(), ho.getPasswordHash())), MediaType.APPLICATION_JSON).build();
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
        LoginsHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no logins with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Login " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new LoginsDao();
            usersDao = new UsersDao();
        }
    }

    // Logins --> JSON document
    private String toJson(Logins logins) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(logins);
        } catch (Exception e) {
        }
        return json;
    }

    // Logins set --> JSON document
    private String toJson(Set<Logins> loginsSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(loginsSet);
        } catch (Exception e) {
        }
        return json;
    }

}

