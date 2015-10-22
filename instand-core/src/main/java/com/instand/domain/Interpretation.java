package com.instand.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.net.URI;
import java.time.Instant;
import java.util.Optional;

/**
 * An interpretation to a subject.
 */
@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = Interpretation.InterpretationBuilder.class)
public class Interpretation {

    /**
     * Interpretation id.
     */
    String id;

    /**
     * Time when this interpretation was created.
     */
    Instant createdAt;

    /**
     * Time when this interpretation was last updated.
     */
    Instant updatedAt;

    /**
     * Id of subject this interpretation belongs to.
     */
    String interpretingSubjectId;

    /**
     * Id of user who created this interpretation.
     */
    String createdByUserId;

    /**
     * Each interpretation has one content.
     * TODO: specify the length limit on the content
     */
    String content;

    /**
     * Each interpretation has zero or one image URI to help better interpret.
     */
    Optional<URI> imageUri;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class InterpretationBuilder {}
}
