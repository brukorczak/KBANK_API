package br.com.ibm.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseMessage {
    @JsonProperty("message")
    String message;

    public ResponseMessage(String errorMessage) {
        this.message = errorMessage;
    }
}
