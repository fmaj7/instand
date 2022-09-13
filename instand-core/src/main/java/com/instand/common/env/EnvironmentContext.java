package com.instand.common.env;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * A containing context of environment information.
 */
@Value
@Builder
public class EnvironmentContext {

    @NonNull
    Stage stage;
}
