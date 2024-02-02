package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class QuestionUpdateDto {
    private String question_id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer isoption;
    private String statement1;
    private String statement2;
    private String statement3;
    private String Image;
    private String created_by;
    private Integer status;
}
