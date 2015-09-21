package com.instand.app;

import lombok.*;

/**
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectInput {

    private String createdByUserId;

    private String title;
}
