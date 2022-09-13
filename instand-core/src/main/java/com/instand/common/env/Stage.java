package com.instand.common.env;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Represents an environment stage.
 */
@Getter
@RequiredArgsConstructor
public enum Stage {

    /**
     * Typically uses for development.
     */
    ALPHA("alpha"),

    /**
     * Typically uses for testing, staging, integration, etc.
     */
    BETA("beta"),

    /**
     * Uses for production.
     */
    PROD("prod");

    /**
     * Id of this stage.
     */
    private final String id;

    /**
     * Returns the enum constant by the give id.
     *
     * @param id stage id
     * @return stage of the id
     * @throws IllegalArgumentException if stage is not found of the code
     */
    public static Stage ofId(@NonNull String id) {
        return Arrays.stream(values())
                .filter(e -> e.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Stage not found of id:"  + id));
    }
}
