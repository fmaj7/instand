package com.instand.domain;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * A subject matter to be explained.
 */
@Value
@Builder(toBuilder = true)
public class Subject implements Entity<String> {

    String id;

    String createdByUserId;

    Instant createdAt;

    String title;
}
