package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class QuestionCreateDto {
    private String question;
    private String category;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private Integer answer;
    private Integer isoption;
    private String statement1;
    private String statement2;
    private String statement3;
    private String Image;
}
