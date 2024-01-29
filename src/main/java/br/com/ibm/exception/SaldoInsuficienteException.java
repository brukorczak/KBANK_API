package br.com.ibm.exception;

import br.com.ibm.util.ResponseMessage;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class SaldoInsuficienteException extends WebApplicationException {
    public SaldoInsuficienteException(String message) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ResponseMessage(message))
                .build());
    }
}