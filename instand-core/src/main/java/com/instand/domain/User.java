package com.instand.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;

/**
 * A user of Instand. It can be an authenticated user or a guest user (i.e., unauthenticated user).
 */
@Value
@Builder(toBuilder = true)
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
     * For authenticated user, the username is the unique username provided by the user during sign-up process. For
     * guest (i.e., unauthenticated) user, the username is a key that Instand locates on behalf of the user to uniquely
     * identifies the guest user in best-effort. It will be in a form of a hash of some natural key (browser-id,
     * app-id, etc.).
     */
    String username;

    /**
     * Represents the account information for an authenticated user. If this user is a guest user, then it will be empty.
     */
    Optional<UserAccount> account;

}