package com.instand.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * A representation of {@link User} that contains only the essential fields. When REST service
 * returns user information to its boundary, {@link UserDescriptor} may be used instead of
 * {@link User} which contains sensitive information like account credentials.
 */
@Value
@Builder
public class UserDescriptor {

    String id;

    Instant createdAt;

    Instant updatedAt;

    String username;
}
