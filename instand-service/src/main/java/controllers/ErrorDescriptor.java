package controllers;

import lombok.Builder;
import lombok.Value;
import ninja.Result;


/**
 * An error object return to a REST client.
 */
@Value
@Builder
public class ErrorDescriptor {

    int status;

    String type;

    String message;

    public static ErrorDescriptor notFound() {
        return ErrorDescriptor.builder()
                .status(Result.SC_404_NOT_FOUND)
                .type("EntityNotFoundException")
                .message("entity is not found")
                .build();
    }

    public static ErrorDescriptor alreadyExists(String message) {
        return ErrorDescriptor.builder()
                .status(Result.SC_400_BAD_REQUEST)
                .type("EntityAlreadyExistsException")
                .message(message)
                .build();
    }

    public static ErrorDescriptor illegalArgument(String message) {
        return ErrorDescriptor.builder()
                .status(Result.SC_400_BAD_REQUEST)
                .type("IllegalArgumentException")
                .message(message)
                .build();
    }
}
