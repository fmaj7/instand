package com.instand.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

/**
 * User account.
 */
@Value
@Builder
@JsonDeserialize(builder = UserAccount.UserAccountBuilder.class)
public class UserAccount {

    /**
     * Email address. Must be unique among all user accounts. Must be final.
     * TODO: Validate password pattern
     */
    String emailAddress;

    /**
     * Password hash.
     */
    String passwordHash;

    public boolean matchesPassword(String password) {
        // hash the password argument
        return password.equals(passwordHash);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserAccountBuilder {}
}
