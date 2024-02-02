package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class QuestionSearchDto {
    private Long question_id;
    private String question;
    private Integer isoption;
    private String dt;
    private Integer status;
    private String created_by;
}
