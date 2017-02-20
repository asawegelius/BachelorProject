/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.endpoints;

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
import se.wegelius.accessservice.dao.ClientDao;
import se.wegelius.accessservice.dao.UserDao;
import se.wegelius.accessservice.model.ho.ClientHO;
import se.wegelius.accessservice.model.Client;
import se.wegelius.accessservice.model.ho.UserHO;

/**
 *
 * @author asawe
 */
@Path("/client")
public class ClientEndpoint {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ClientEndpoint.class);
    //@Context
    private static ClientDao dao;
    private static UserDao userDao;

    public ClientEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<ClientHO> set = dao.getAll();
        Set<Client> client = new HashSet<>();
        for (ClientHO ho : set) {
            client.add(new Client(ho.getClientId(), ho.getUser().getUserId(), ho.getClientName(), ho.getClientDescription(), ho.getRedirectUri()));
        }
        return Response.ok(toJson(client), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        ClientHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Client(ho.getClientId(), ho.getUser().getUserId(), ho.getClientName(), ho.getClientDescription(), ho.getRedirectUri())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{user_id: \\d+}/{client_name}/{client_description}/{redirect_uri}")
    public Response createJson(
            @Min(value = 0, message = "The user id should not be less that 0. ")
            @NotNull(message = "You need to state the user id. ")
            @PathParam("user_id") Integer user_id,
            @Size(max = 45, message = "The client name length should be "
                    + "max 45 character. ")
            @NotNull(message = "You need to state the client name. ")
            @PathParam("client_name") String client_name,
            @Size(max = 2048, message = "The description length should be "
                    + "max 2048 character. ")
            @PathParam("client_description") String client_description,
            @Size(max = 1024, message = "The redirect uri length should be "
                    + "max 1024 character. ")
            @NotNull(message = "You need to state the redirect urs. ")
            @PathParam("redirect_urs") String redirect_uri) throws ValidationException {
        checkContext();
        // user need to exist
        UserHO user = userDao.findByID(user_id);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There are no user with id " + user_id).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // Otherwise, create the Client and add it to the database.
        ClientHO ho = new ClientHO();
        ho.setUser(user);
        ho.setClientName(client_name);
        ho.setClientDescription(client_description);
        ho.setRedirectUri(redirect_uri);
        dao.save(ho);
        return Response.ok(toJson(new Client(ho.getClientId(), ho.getUser().getUserId(), ho.getClientName(), ho.getClientDescription(), ho.getRedirectUri())), "application/json").build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("update/{client_id: \\d+}/{user_id: \\d+}/{client_name}/{client_description}/{redirect_uri}")
    public Response updateJson(
            @Min(value = 0, message = "The client id should not be less that 0. ")
            @NotNull(message = "You need to state the client id. ")
            @PathParam("client_id") Integer client_id,
            @Min(value = 0, message = "The user id should not be less that 0. ")
            @NotNull(message = "You need to state the user id. ")
            @PathParam("user_id") Integer user_id,
            @Size(min = 2, max = 45, message = "The client name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the client name. ")
            @PathParam("client_name") String client_name,
            @Size(max = 2048, message = "The description length should be "
                    + "less than 2048 character. ")
            @PathParam("client_description") String client_description,
            @Size(max = 1024, message = "The redirect uri length should be "
                    + "less than 1024 character. ")
            @NotNull(message = "You need to state the redirect urs. ")
            @PathParam("redirect_urs") String redirect_uri) throws ValidationException {
        checkContext();
        ClientHO ho = dao.findByID(client_id);
        if (ho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There are no client with id " + client_id).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        UserHO uho = userDao.findByID(user_id);
        if (uho == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There are no user with id " + user_id).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // Update.
        ho.setUser(uho);
        ho.setClientName(client_name);
        ho.setClientDescription(client_description);
        ho.setRedirectUri(redirect_uri);
        dao.update(ho);
        return Response.ok(toJson(new Client(ho.getClientId(), ho.getUser().getUserId(), ho.getClientName(), ho.getClientDescription(), ho.getRedirectUri())), "application/json").build();
 
    }

    @DELETE
        @Produces({MediaType.APPLICATION_JSON})
        @Path("/delete/{id: \\d+}")
        public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg;
        ClientHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no client with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Client " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new ClientDao();
            userDao = new UserDao();
        }
    }

    // Client --> JSON document
    private String toJson(Client client) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(client);
        } catch (Exception e) {
        }
        return json;
    }

    // Client set --> JSON document
    private String toJson(Set<Client> clientSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(clientSet);
        } catch (Exception e) {
        }
        return json;
    }

}
