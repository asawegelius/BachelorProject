/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.endpoints;

/**
 *
 * @author asawe
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.DELETE;
import javax.ws.rs.QueryParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import se.wegelius.identityservice.dao.UsersDao;
import se.wegelius.identityservice.model.HO.UsersHO;
import se.wegelius.identityservice.model.Users;

/**
 *
 * @author asawe
 */
@Path("/users")
public class UsersEndpoint {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UsersEndpoint.class);
    //@Context
    private static UsersDao dao;

    public UsersEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getJson() {
        checkContext();
        Set<UsersHO> set = dao.getAll();
        Set<Users> users = new HashSet<>();
        for (UsersHO ho : set) {
            users.add(new Users(ho.getUsersId(), ho.getFirstName(), ho.getLastName(), ho.getEmail(), ho.getMobile()));
        }
        return Response.ok(toJson(users), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        UsersHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Users(ho.getUsersId(), ho.getFirstName(), ho.getLastName(), ho.getEmail(), ho.getMobile())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create")
    public Response createJson(
            @Size(min = 2, max = 45, message = "The first name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the first name. ")
            @QueryParam("first_name") String first_name,
            @Size(min = 2, max = 45, message = "The last name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the last name. ")
            @QueryParam("last_name") String last_name,
            @NotNull(message = "You need to state the e-mail. ")
            @QueryParam("email") String mail,
            @QueryParam("mobile") String mobile) {
        checkContext();
        System.out.println("will try to create user " + first_name + " " + last_name + " " + mail);
        String msg = "";

        // Require correct mail to create.
        if (!validateEmail(mail)) {
            msg += "the email is incorrect";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        if (!(mobile == null) && !validatePhone(mobile)) {
            msg += "the phone number is incorrect";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        // Otherwise, create the Events and add it to the database.
        UsersHO ho = new UsersHO();
        ho.setFirstName(first_name);
        ho.setLastName(last_name);
        ho.setEmail(mail);
        ho.setMobile(mobile);

        dao.save(ho);
        
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(ho);
        } catch (Exception e) {
            System.out.println("json parse error occured");
        }

        System.out.println(json + " the json string");
        return Response.ok(json, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/update")
    public Response updateJson(
            @Min(value = 0, message = "The user id should not be less that 0. ")
            @NotNull(message = "You need to state the user id. ")
            @QueryParam("user_id") Integer user_id,
            @Size(min = 2, max = 45, message = "The first name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the first name. ")
            @QueryParam("first_name") String first_name,
            @Size(min = 2, max = 45, message = "The last name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the last name. ")
            @QueryParam("last_name") String last_name,
            @NotNull(message = "You need to state the e-mail. ")
            @QueryParam("email") String mail,
            @QueryParam("mobile") String mobile) {
        checkContext();
        UsersHO ho = dao.findByID(user_id);
        // Check that sufficient data are present to do an edit.
        String msg = "";
        // Require correct mail to create.
        if (!validateEmail(mail)) {
            msg += "the email is incorrect";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        if (!(mobile == null) && !validatePhone(mobile)) {
            msg += "the phone number is incorrect";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // Update.
        ho.setFirstName(first_name);
        ho.setLastName(last_name);
        ho.setEmail(mail);
        ho.setMobile(mobile);
        dao.update(ho);
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(ho);
        } catch (Exception e) {
            System.out.println("json parse error occured");
        }
        return Response.ok(json, MediaType.APPLICATION_JSON).build();

    }

    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/delete/{id: \\d+}")
    public Response delete(@PathParam("id") int id) {
        checkContext();
        String msg;
        UsersHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no user with id " + id + ". Cannot delete.\n";
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
            dao = new UsersDao();
        }
    }

    // Users --> JSON document
    private String toJson(Users user) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(user);
        } catch (Exception e) {
            System.out.println("json parse error occured");
        }
        return json;
    }

    // Users set --> JSON document
    private String toJson(Set<Users> userSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(userSet);
        } catch (Exception e) {
            System.out.println("json parse error occured");
        }
        return json;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX
            = Pattern.compile("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", Pattern.CASE_INSENSITIVE);

    public boolean validateEmail(String emailStr) {
        System.out.println("validating email   " + emailStr);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        boolean valid = matcher.find();
        System.out.println("validating email  and got " + valid);
        return valid;
    }

    /**
     * Regular expression for a Danish phone number
     */
    public static final Pattern VALID_PHONE_NUMBER_REGEX
            = Pattern.compile("^(?!\\s*$)[0-9\\s]{8}$", Pattern.CASE_INSENSITIVE);

    public boolean validatePhone(String phoneStr) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneStr);
        return matcher.find();
    }
}
