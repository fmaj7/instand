package controllers;

import lombok.Builder;
import lombok.Value;
import ninja.Result;


/**
 * An error object return to a REST client.
 */
@Value
@Builder
public class Error {

    int status;

    String type;

    String message;

    public static Error notFound() {
        return Error.builder()
                .status(Result.SC_404_NOT_FOUND)
                .type("EntityNotFoundException")
                .message("entity is not found")
                .build();
    }
}
