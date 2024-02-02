package org.app.edufun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.app.edufun.Configuration.filters.MSRequestFilter;
import org.app.edufun.constant.VMessageConstant;
import org.app.edufun.dto.QuestionCreateDto;
import org.app.edufun.dto.QuestionSearchDto;
import org.app.edufun.dto.QuestionUpdateDto;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/EduFun/Questions")
@Api(value = "EduFun_microservice", description = "Questions Management Operations", tags = "Questions Controller")
public class QuestionController {
    private QuestionService questionService;
    private final MSRequestFilter msRequestFilter;

    @PostMapping("/add/question")
    @ApiOperation(value = "add new question record", notes = "This Rest API adds a new question record.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> addQuestion(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody QuestionCreateDto questionCreateDto) {
        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);
        return new ResponseEntity<>(questionService.addQuestion(session, questionCreateDto), HttpStatus.OK);
    }

    @PostMapping("/search/question")
    @ApiOperation(value = "search Questions", notes = "This API fetches Questions based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> searchQuestion(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody QuestionSearchDto questionSearchDto) {
        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);
        return new ResponseEntity<>(questionService.searchQuestion(questionSearchDto), HttpStatus.OK);
    }

    @PostMapping("/Innersearch/question")
    @ApiOperation(value = "search Questions", notes = "This API fetches Questions based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> searchQues(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody QuestionSearchDto questionSearchDto) {
        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);
        return new ResponseEntity<>(questionService.searchQues(session, questionSearchDto), HttpStatus.OK);
    }

    @PostMapping("/update/question")
    @ApiOperation(value = "Update Question", notes = "This API update Question resources based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> updateQuestion(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody QuestionUpdateDto questionUpdateDto) {
        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);
        return new ResponseEntity<>(questionService.updateQuestion(questionUpdateDto), HttpStatus.OK);
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



