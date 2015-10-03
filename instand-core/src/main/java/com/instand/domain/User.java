package com.instand.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;

/**
 * A user of Instand. It can be an registered user or a guest user (i.e., unregistered user).
 */
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {

    /**
     * A unique surrogate key assigned to each user.
     */
    String id;

    /**
     * Time when this user was created.
     */
    Instant createdAt;

    /**
     * Time when this user was last updated.
     */
    Instant updatedAt;

    /**
     * For registered user, the username is the unique username provided by the user during
     * registration process. For guest (i.e., unregistered) user, the username is a key that Instand
     * locates on behalf of the user to uniquely identifies the guest user in best-effort.
     * It will be in a form of a hash of some natural key (browser-id, app-id, etc.).
     *
     * TODO: validate username pattern
     */
    String username;

    /**
     * Represents the account information for an register user. If this user is a guest user,
     * then it will be empty.
     */
    Optional<UserAccount> account;

    /**
     * Returns whether this user is registered.
     *
     * @return whether this user is registered
     */
    @JsonIgnore
    public boolean isRegisteredUser() {
        return account.isPresent();
    }

    /**
     * Return whether this user is guest.
     *
     * @return whether this user is guest
     */
    @JsonIgnore
    public boolean isGuestUser() {
        return !isRegisteredUser();
    }

    /**
     * Return email address of the user.
     *
     * @return email address of the user
     */
    @JsonIgnore
    public Optional<String> getEmailAddress() {
        if (account.isPresent()) {
            return Optional.of(account.get().getEmailAddress());
        }
        return Optional.empty();
    }

    public UserDescriptor toDescriptor() {
        return UserDescriptor.builder()
                .id(getId())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .username(getUsername())
                .build();
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class UserBuilder {}
}