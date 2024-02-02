package org.app.edufun.validation;

import org.apache.commons.lang3.StringUtils;
import org.app.edufun.dao.UserRepository;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.dto.SubmissionCreateDto;
import org.app.edufun.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class SubmissionValidation {
    public ResponseDto addSubmission(SubmissionCreateDto submissionCreateDto) {

        if (submissionCreateDto.getQuestion_id() == null) {
            return ResponseDto.builder().status(false).message("Question ID is mandatory!").build();
        }  else if (StringUtils.isBlank(submissionCreateDto.getSubmissionTime())) {
            return ResponseDto.builder().status(false).message("Submission Time is mandatory!").build();
        } else if (submissionCreateDto.getStatus() == null) {
            return ResponseDto.builder().status(false).message("Status is mandatory!").build();
        } else {
            return ResponseDto.builder().status(true).build();
        }
    }
}
