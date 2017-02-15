package se.wegelius.speedvoter.voteservice.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationException implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                .type(MediaType.APPLICATION_JSON)
                .entity(e.getMessage())
                .build();
    }
}