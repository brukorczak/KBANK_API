package br.com.ibm.exception;

import br.com.ibm.util.ResponseMessage;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {
    public BadRequestException(String message){
        super(Response.status(
                        Response
                                .Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ResponseMessage(message))
                .build());
    }
}
