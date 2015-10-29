package com.instand.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.util.Optional;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateInterpretationInput {

    String interpretingSubjectId;

    String createdByUserId;

    String content;

    String imageUri;
}
