package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class SubmissionCreateDto {
    private Long question_id;
    private String submissionTime;
    private Integer status;
}
