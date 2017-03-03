/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.wegelius.identityservice.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.LoggerFactory;
import se.wegelius.identityservice.dao.IGenericDao;
import se.wegelius.identityservice.model.HO.LoginsHO;
import se.wegelius.identityservice.model.Logins;

/**
 *
 * @author asawe
 */
@Path("/access")
public class AccessEndpoint {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginsEndpoint.class);
    //@Context
    private static IGenericDao dao;

    public AccessEndpoint() {
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccess(@HeaderParam("Authorization") String authorization) {
//        String[] credentials = authorization.split(":");
/*
        Set<LoginsHO> set = dao.getAll();
        Set<Logins> logins = new HashSet<>();
        for (LoginsHO ho : set) {
            logins.add(new Logins(ho.getLoginsId(), ho.getUsers().getEmail(), ho.getPasswordSalt(), ho.getPasswordHash()));
        }*/
                String json = "If you see this, there's a problem.";
        try {
            //json = new ObjectMapper().writeValueAsString(logins);
        } catch (Exception e) {
        }
        return Response.ok(json, "application/json").build();
    }
}
