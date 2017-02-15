/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.speedvoter.voteservice.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import se.wegelius.speedvoter.voteservice.dao.OrgsDao;
import se.wegelius.speedvoter.voteservice.dao.VotersDao;
import se.wegelius.speedvoter.voteservice.model.Voter;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.OrgsHO;
import se.wegelius.speedvoter.voteservice.model.hibernateobjects.VoterHO;

/**
 *
 * @author asawe
 */
@Path("/voters")
public class VotersEndpoint {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(VotersEndpoint.class);
    //@Context
    private static VotersDao dao;
    private static OrgsDao orgsDao;

    public VotersEndpoint() {
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/")
    public Response getJson() {
        checkContext();
        Set<VoterHO> set = dao.getAll();
        Set<Voter> options = new HashSet<>();
        for (VoterHO ho : set) {
            options.add(new Voter(ho.getVoterId(), ho.getMemberCode(), ho.getName(), ho.getMail(), ho.getMobil()));
        }
        return Response.ok(toJson(options), "application/json").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/{id: \\d+}")
    public Response getJson(@PathParam("id") int id) {
        checkContext();
        VoterHO ho = dao.findByID(id);
        if (ho == null) {
            String msg = id + " is a bad ID.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        } else {
            return Response.ok(toJson(new Voter(ho.getVoterId(), ho.getMemberCode(), ho.getName(), ho.getMail(), ho.getMobil())), "application/json").build();
        }
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Path("/create/{orgs_id}/{memberCode}/{name}/{mail}/{mobil}")
    public Response createJson(
            @Min(value = 0, message = "The org id should not be less that 0. ")
            @NotNull(message = "You need to state the org id. ")
            @PathParam("org_id") Integer org_id,
            @NotNull(message = "You need to state the member code. ")
            @PathParam("memberCode") String memberCode,
            @Size(min = 2, max = 60, message = "The name length should be "
                    + "between 2 and 60 character. ")
            @NotNull(message = "You need to state the name. ")
            @PathParam("name") String name,
            @PathParam("mail") String mail,
            @PathParam("mobile") String mobile) throws ValidationException {
        checkContext();
        // Required parameters to create.

        OrgsHO orgHo = orgsDao.findByID(org_id);
        if (orgHo == null) {
            return Response.status(Response.Status.BAD_REQUEST).
                    entity("There is no org with id " + org_id).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }

        // Otherwise, create the Voter and add it to the database.
        VoterHO ho = new VoterHO();
        ho.setOrgs(orgHo);
        ho.setMemberCode(memberCode);
        ho.setName(name);
        ho.setMail(mail);
        ho.setMobil(mobile);
        dao.save(ho);
        return Response.ok(toJson(new Voter(ho.getOrgs().getOrgId(), ho.getMemberCode(), ho.getName(),
                ho.getMail(), ho.getMobil())), MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    @Path("update/{voter_id: \\d+}/{orgs_id}/{memberCode}/{name}/{mail}/{mobil}")
    public Response updateJson(
            @Min(value = 0, message = "The voter id should not be less that 0. ")
            @NotNull(message = "You need to state the voter id. ")
            @PathParam("voter_id") Integer voter_id,
            @Min(value = 0, message = "The org id should not be less that 0. ")
            @NotNull(message = "You need to state the org id. ")
            @PathParam("org_id") Integer org_id,
            @Size(min = 2, max = 45, message = "The name length should be "
                    + "between 2 and 45 character. ")
            @NotNull(message = "You need to state the name. ")
            @PathParam("name") String name,
            @PathParam("mail") String mail,
            @PathParam("mobile") String mobile) throws ValidationException {
        checkContext();
        VoterHO ho = dao.findByID(voter_id);
        OrgsHO orgho = orgsDao.findByID(org_id);
        // Check that sufficient data are present to do an edit.
        String msg = "";
        if (ho == null) {
            msg += "There is no voter with id " + voter_id + "\n";
        } else if (orgho == null) {
            msg += "There is no org with id " + org_id + "\n";
        } else {
            boolean mail_ok = true;
            boolean mobile_ok = true;
            // Update.
            if (!(mail==null) && !validateEmail(mail)) {
                mail_ok = false;
                msg += "the email is incorrect";
            }
            if (!(mobile == null) && !validatePhone(mobile)) {
                mobile_ok = false;
                msg += "the phone number is incorrect";
            }
            if (mail_ok && mobile_ok) {
                ho.setOrgs(orgho);
                ho.setName(name);
                dao.update(ho);
                return Response.ok(toJson(new Voter(ho.getOrgs().getOrgId(), ho.getMemberCode(), ho.getName(),
                ho.getMail(), ho.getMobil())), MediaType.APPLICATION_JSON).build();
            }
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
        VoterHO ho = dao.findByID(id);
        if (ho == null) {
            msg = "There is no voter with id " + id + ". Cannot delete.\n";
            return Response.status(Response.Status.BAD_REQUEST).
                    entity(msg).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
        dao.delete(ho);
        msg = "Voter " + id + " deleted.";

        return Response.status(Response.Status.OK).
                entity(msg).
                type(MediaType.APPLICATION_JSON).
                build();
    }

    //** utilities
    private void checkContext() {
        if (dao == null) {
            dao = new VotersDao();
            orgsDao = new OrgsDao();
        }
    }

    // Voters --> JSON document
    private String toJson(Voter voter) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(voter);
        } catch (Exception e) {
        }
        return json;
    }

    // Voters set --> JSON document
    private String toJson(Set<Voter> voterSet) {
        String json = "If you see this, there's a problem.";
        try {
            json = new ObjectMapper().writeValueAsString(voterSet);
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
