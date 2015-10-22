package com.instand.app;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateSubjectInput {

    String createdByUserId;

    String title;
}
