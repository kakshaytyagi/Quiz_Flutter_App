package org.app.edufun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.app.edufun.Configuration.filters.MSRequestFilter;
import org.app.edufun.constant.VMessageConstant;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.dto.SubmissionCreateDto;
import org.app.edufun.dto.SubmissionSearchDto;
import org.app.edufun.service.SubmissionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/EduFun/Submission")
@Api(value = "EduFun_microservice", description = "Submission Management Operations", tags = "Submission Controller")
public class SubmissionController {
    private SubmissionService submissionService;
    private final MSRequestFilter msRequestFilter;

    @PostMapping("/add/Submission")
    @ApiOperation(value = "add new Submission record", notes = "This Rest API adds a new Submission record.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> addSubmission(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody SubmissionCreateDto submissionCreateDto) {
        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);
        return new ResponseEntity<>(submissionService.addSubmission(session, submissionCreateDto), HttpStatus.OK);
    }

    @PostMapping("/search/Submission")
    @ApiOperation(value = "search Submission", notes = "This API fetches Submission based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> searchSubmission(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody SubmissionSearchDto submissionSearchDto) {

        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);

        return new ResponseEntity<>(submissionService.searchSubmission(session, submissionSearchDto), HttpStatus.OK);
    }

    @PostMapping("/Question/Topper")
    @ApiOperation(value = "search Submission", notes = "This API fetches Submission based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> searchTopper(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody SubmissionSearchDto submissionSearchDto) {

        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);

        return new ResponseEntity<>(submissionService.searchTopper(session, submissionSearchDto), HttpStatus.OK);
    }

    @PostMapping("/Top/Performer")
    @ApiOperation(value = "search Submission", notes = "This API fetches Submission based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> searchTopPerformer(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody SubmissionSearchDto submissionSearchDto) {

        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);

        return new ResponseEntity<>(submissionService.searchTopPerformer(session), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> iSessionRequired(HttpSession session) {
        try {
            if (session.getAttribute("isSession") != "true") {
                return new ResponseEntity<>(ResponseDto.builder().message("Please login!").status(false).body(null).build(), HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(ResponseDto.builder().message(VMessageConstant.DEFAULT).status(false).body(null).build(), HttpStatus.OK);
        }
        return null;
    }
}




