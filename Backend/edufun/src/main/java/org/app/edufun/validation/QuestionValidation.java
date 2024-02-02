package org.app.edufun.validation;

import org.apache.commons.lang3.StringUtils;
import org.app.edufun.dto.QuestionCreateDto;
import org.app.edufun.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public class QuestionValidation {

    public ResponseDto addQuestion(QuestionCreateDto questionCreateDto) {

        if (StringUtils.isBlank(questionCreateDto.getQuestion())) {
            return ResponseDto.builder().status(false).message("Question is mandatory!").build();
        }else if (StringUtils.isBlank(questionCreateDto.getCategory())) {
            return ResponseDto.builder().status(false).message("Category is mandatory!").build();
        } else if (questionCreateDto.getIsoption() == 1) {
            if (questionCreateDto.getStatement1() == null) {
                return ResponseDto.builder().message("Answer is mandatory!").status(false).body(null).build();
            }
        } else if (questionCreateDto.getIsoption() == 0) {
            if (questionCreateDto.getOption1() == null && questionCreateDto.getOption2() == null && questionCreateDto.getOption3() == null && questionCreateDto.getOption4() == null) {
                return ResponseDto.builder().message("All Options are mandatory!").status(false).body(null).build();
            }
        }
        return ResponseDto.builder().status(true).build();
    }
}

