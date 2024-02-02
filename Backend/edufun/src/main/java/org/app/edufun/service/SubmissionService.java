package org.app.edufun.service;

import lombok.AllArgsConstructor;
import org.app.edufun.dao.SubmissionRepository;
import org.app.edufun.dto.*;
import org.app.edufun.entity.Submission;
import org.app.edufun.entity.TopPeformer;
import org.app.edufun.entity.TopperInfo;
import org.app.edufun.validation.SubmissionValidation;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
@Service
@AllArgsConstructor
@Transactional
public class SubmissionService {
    private SubmissionRepository submissionRepository;
    private final SubmissionValidation submissionValidation;
//    public ResponseDto addSubmission(HttpSession session, SubmissionCreateDto submissionCreateDto) {
//        ResponseDto response = submissionValidation.addSubmission(submissionCreateDto);
//        if (!response.isStatus()) {
//            return response;
//        }
//
//        java.sql.Time submissionTime = java.sql.Time.valueOf(submissionCreateDto.getSubmissionTime());
//
//        Submission submission = Submission.builder()
//                .user_id((Long) session.getAttribute("user_id"))
////                .user_id(submissionCreateDto.getUser_id())
//                .question_id(submissionCreateDto.getQuestion_id())
//                .submissionTime(submissionTime)
//                .dt(Instant.now())
//                .status(submissionCreateDto.getStatus())                       // Replace with the appropriate status value
//                .build();
//
//
//
//        boolean isInserted = submissionRepository.saveSubmission(submission);
//        if (isInserted) {
//            return ResponseDto.builder().message("Submission added successfully!").status(true).body(submission).build();
//        } else {
//            return ResponseDto.builder().message("New Submission is not created!").status(false).body(null).build();
//        }
//    }

    public ResponseDto addSubmission(HttpSession session, SubmissionCreateDto submissionCreateDto) {
        try {
            ResponseDto response = submissionValidation.addSubmission(submissionCreateDto);
            if (!response.isStatus()) {
                return response;
            }

            String submissionTimeString = submissionCreateDto.getSubmissionTime();

            if (submissionTimeString == null || submissionTimeString.isEmpty()) {
                return ResponseDto.builder()
                        .message("SubmissionTime is missing or empty.")
                        .status(false)
                        .body(null)
                        .build();
            }
//
//
//
//            if (!submissionTimeString.matches("\\d{2}:\\d{2}:\\d{3}")) {
//                throw new IllegalArgumentException("Invalid submissionTime format. Use mm:ss:millisec.");
//            }
//

            Submission submission = Submission.builder()
                    .user_id((Long) session.getAttribute("user_id"))
                    .question_id(submissionCreateDto.getQuestion_id())
                    .submissionTime(submissionTimeString) // Store as a string
                    .dt(Instant.now())
                    .status(submissionCreateDto.getStatus())
                    .build();

            boolean isInserted = submissionRepository.saveSubmission(submission);
            if (isInserted) {
                return ResponseDto.builder()
                        .message("Submission added successfully!")
                        .status(true)
                        .body(submission)
                        .build();
            } else {
                return ResponseDto.builder()
                        .message("New Submission was not created!")
                        .status(false)
                        .body(null)
                        .build();
            }
        } catch (Exception e) {
            return ResponseDto.builder()
                    .message("Error: " + e.getMessage())
                    .status(false)
                    .body(null)
                    .build();
        }
    }


    public ResponseDto searchSubmission(HttpSession session, SubmissionSearchDto submissionSearchDto) {
        try {
            List<Submission> list = submissionRepository.searchSubmission(session, submissionSearchDto);
            if (list == null || list.isEmpty()) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }

    }

    public ResponseDto searchTopper(HttpSession session, SubmissionSearchDto submissionSearchDto) {
        try {
            List<TopperInfo> list = submissionRepository.searchQuestionTopper(session, submissionSearchDto);
            if (list == null || list.isEmpty()) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }

    }

    public ResponseDto searchTopPerformer(HttpSession session) {
        try {
            List<TopPeformer> list = submissionRepository.searchTopPerformer(session);
            if (list == null || list.isEmpty()) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }

    }

    public ResponseDto getById(long submission_id) {
        try {
            Submission list = submissionRepository.searchById(submission_id);
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

