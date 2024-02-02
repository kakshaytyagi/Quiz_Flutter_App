package org.app.edufun.service;

import lombok.AllArgsConstructor;
import org.app.edufun.dao.QuestionRepository;
import org.app.edufun.dto.*;
import org.app.edufun.entity.Questions;
import org.app.edufun.entity.common.QuestionType;
import org.app.edufun.validation.QuestionValidation;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class QuestionService {
    private QuestionRepository questionRepository;
    private final QuestionValidation questionValidation;
    public ResponseDto addQuestion(HttpSession session, QuestionCreateDto questionCreateDto) {
        ResponseDto response = questionValidation.addQuestion(questionCreateDto);
        if (!response.isStatus()) {
            return response;
        }


        Questions question = Questions.builder()
                .question(questionCreateDto.getQuestion())
                .user_id((Long)session.getAttribute("user_id"))
                .option1(questionCreateDto.getOption1() != null ? questionCreateDto.getOption1() : null)
                .option2(questionCreateDto.getOption2() != null ? questionCreateDto.getOption2() : null)
                .option3(questionCreateDto.getOption3() != null ? questionCreateDto.getOption3() : null)
                .option4(questionCreateDto.getOption4() != null ? questionCreateDto.getOption4() : null)
                .isoption(questionCreateDto.getIsoption())
                .answer(questionCreateDto.getAnswer() != null ? questionCreateDto.getAnswer() : null)
                .category(QuestionType.valueOf(questionCreateDto.getCategory()))
                .statement1(questionCreateDto.getStatement1() != null ? questionCreateDto.getStatement1() : null)
                .statement2(questionCreateDto.getStatement2() != null ? questionCreateDto.getStatement2() : null)
                .statement3(questionCreateDto.getStatement3() != null ? questionCreateDto.getStatement3() : null)
                .image(questionCreateDto.getImage() != null ? questionCreateDto.getImage() : null)
                .created_by((String) session.getAttribute("username"))
                .dt(Instant.now())
                .status(0)
                .build();


        boolean isInserted = questionRepository.saveQuestion(question);
        if (isInserted) {
            return ResponseDto.builder().message("Question added successfully!").status(true).body(question).build();
        } else {
            return ResponseDto.builder().message("New Question is not created!").status(false).body(null).build();
        }
    }

    public ResponseDto searchQuestion( QuestionSearchDto questionSearchDto) {
        try {
            List<Questions> list = questionRepository.searchQuestion(questionSearchDto);
            if (list == null || list.isEmpty()) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }

    }

    public ResponseDto searchQues(HttpSession session, QuestionSearchDto questionSearchDto) {
        try {
            List<Questions> list = questionRepository.searchQues(session, questionSearchDto);
            if (list == null || list.isEmpty()) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }

    }

    public ResponseDto updateQuestion(QuestionUpdateDto questionUpdateDto) {
        try {
            if (questionRepository.updateQuestion(questionUpdateDto) > 0) {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body("Question has been updated").build();
            } else {
                return ResponseDto.builder().message("Sorry! The request has not been executed.").status(false).body("Question has not been updated").build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }
    }

    public ResponseDto getById(long question_id) {
        try {
            Questions list = questionRepository.searchById(question_id);
            if (list == null) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }
    }
}
