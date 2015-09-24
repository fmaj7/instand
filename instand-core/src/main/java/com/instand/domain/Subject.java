package com.instand.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

/**
 * A subject matter to be explained.
 */
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Subject.SubjectBuilder.class)
public class Subject implements Entity<String> {

    /**
     * A unique surrogate key assigned to each subject.
     */
    String id;

    /**
     * Time when the subject was created.
     */
    Instant createdAt;

    /**
     * User id who created this subject
     */
    String createdByUserId;

    /**
     * Title of this subject
     */
    String title;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class SubjectBuilder {}
}
