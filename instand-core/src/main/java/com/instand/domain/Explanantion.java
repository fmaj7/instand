package com.instand.domain;

import lombok.Value;

import java.time.Instant;

/**
 * An explanantion to a subject.
 */
@Value
public class Explanantion {

    String id;

    Instant createdAt;

    String owningSubjectId;

    String owningUserId;

    String contentHtml;
}
