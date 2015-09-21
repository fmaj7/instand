package com.instand.domain;

import lombok.Value;

/**
 * User account.
 */
@Value
public class UserAccount {

    /**
     * Username. Must be unique among all user accounts. Must be final.
     */
    String username;

    /**
     * Email address. Must be unique among all user accounts. Must be final.
     */
    String email;

    /**
     * Password hash.
     */
    String passwordHash;

}
