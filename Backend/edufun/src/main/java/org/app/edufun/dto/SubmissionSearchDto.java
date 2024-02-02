package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class SubmissionSearchDto {
    private Long submission_id;
    private Long question_id;
    private Long user_id;
    private String dt;
    private Integer status;
}
