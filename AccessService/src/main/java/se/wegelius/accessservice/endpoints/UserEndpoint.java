/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.accessservice.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
import se.wegelius.accessservice.dao.UserDao;
import se.wegelius.accessservice.model.User;
import se.wegelius.accessservice.model.ho.UserHO;

/**
 *
 * @author asawe
 */
@Path("/Users")
public class UserEndpoint {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserEndpoint.class);
    //@Context
    private static UserDao dao;

    public UserEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<UserHO> set = dao.getAll();
        Set<User> Users = new HashSet<>();
        for (UserHO ho : set) {
            Users.add(new User(ho.getUserId(), ho.getUserName()));
        }
        return Response.ok(toJson(Users), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        UserHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new User(ho.getUserId(), ho.getUserName())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{user_name}")
    public Response createJson(
            @Size(min = 2, max = 45, message = "The user name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the first name. ")
            @PathParam("user_name") String user_name) {
        checkContext();
        // Check user name is unique
        // TODO: 
        // Otherwise, create the Events and add it to the database.
        UserHO ho = new UserHO();
        ho.setUserName(user_name);
        dao.save(ho);
        return Response.ok(toJson(new User(ho.getUserId(), ho.getUserName())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update/{id: \\d+}/{user_name}")
    public Response updateJson(
            @Min(value = 0, message = "The User id should not be less that 0. ")
            @NotNull(message = "You need to state the User id. ")
            @PathParam("User_id") Integer User_id,
            @Size(min = 2, max = 45, message = "The first name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the user name. ")
            @PathParam("user_name") String user_name) {
        checkContext();
        UserHO ho = dao.findByID(User_id);
        // Check that sufficient data are present to do an edit.



        // Update.
        ho.setFirstName(user_name);
        ho.setLastName(last_name);
        ho.setEmail(mail);
        ho.setMobile(mobile);
        dao.update(ho);
        return Response.ok(toJson(new User(ho.getUserId(), ho.getFirstName(), ho.getLastName(), ho.getEmail(), ho.getMobile())), MediaType.APPLICATION_JSON).build();

    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg;
        UserHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no User with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "User " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new UserDao();
        }
    }

    // User --> JSON document
    private String toJson(User User) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(User);
        } catch (Exception e) {
        }
        return json;
    }

    // User set --> JSON document
    private String toJson(Set<User> UserSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(UserSet);
        } catch (Exception e) {
        }
        return json;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    /**
     * Regular expression for a Danish phone number
     */
    public static final Pattern VALID_PHONE_NUMBER_REGEX
            = Pattern.compile("^((\\(?\\+45\\)?)?)(\\s?\\d{2}\\s?\\d{2}\\s?\\d{2}\\s?\\d{2})$", Pattern.CASE_INSENSITIVE);

    public static boolean validatePhone(String phoneStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(phoneStr);
        return matcher.find();
    }
}

