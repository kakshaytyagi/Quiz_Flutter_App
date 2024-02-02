package org.app.edufun.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.app.edufun.Configuration.filters.MSRequestFilter;
import org.app.edufun.constant.VMessageConstant;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.dto.UserCreateDto;
import org.app.edufun.dto.UserUpdateDto;
import org.app.edufun.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/EduFun/User")
@Api(value = "EduFun_microservice", description = "User Management Operations", tags = "User Controller")
public class UserController {
    private UserService userService;
    private final MSRequestFilter msRequestFilter;

    @PostMapping("/add/User")
    @ApiOperation(value = "add new User record", notes = "This Rest API adds a new User record.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> addUser(@RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userService.addUser(userCreateDto), HttpStatus.OK);
    }

    @PostMapping("/update/User")
    @ApiOperation(value = "Update User", notes = "This API update user resources based on specified parameters")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad request error"),
            @ApiResponse(code = 401, message = "Unauthorized client error"),
            @ApiResponse(code = 403, message = "Forbidden client error"),
            @ApiResponse(code = 404, message = "Invalid data"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public ResponseEntity<ResponseDto> updateUser(HttpSession session, @RequestHeader("Authorization") String token, @RequestBody UserUpdateDto userUpdateDto) {
        msRequestFilter.validateAndSetSessionAttributes(session, token);
        this.iSessionRequired(session);

        System.out.println(userUpdateDto.getCount());
        return new ResponseEntity<>(userService.updateUser(session, userUpdateDto), HttpStatus.OK);
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



