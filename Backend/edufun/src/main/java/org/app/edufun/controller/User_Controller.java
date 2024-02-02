package org.app.edufun.controller;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//
//import io.swagger.annotations.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.app.edufun.dto.ResponseDto;
//import org.app.edufun.entity.User;
//import org.app.edufun.service_impl.User_Service_Impl;
//
//@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RestController
//@RequestMapping(value = "/Recruit/User")
////@CrossOrigin(origins = { "*" }, maxAge = 4800,methods = {""}, allowCredentials = "false", allowedHeaders = {"*"} )
//@Api(value = "Recruiter_microservice", description = "To varify user", tags = "Login System")
//public class User_Controller {
//
//
//	@Autowired
//	private User_Service_Impl user_service;
//
//	final String username_pattern = "([a-zA-Z])";
//	final String contact_pattern = "([0-9]+)";
//	final String role_pattern = "([a-zA-Z])";
//
//	@GetMapping(value = "/login")
//	@ApiOperation(value = " login with username and password", notes = "This Rest Api authorize the User")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> login(HttpServletRequest request,
//			@ApiParam(value = "Enter your email id", required = false) @RequestParam(defaultValue = "", required = true) String username,
//			@ApiParam(value = "Enter your correct password", required = false) @RequestParam(defaultValue = "", required = true) String password) {
//
////			HttpSession session = request.getSession();
//			System.out.println("user =" + request.getSession().getId());
////			session.setAttribute("userName", username);
//			return user_service.varifyUser(request,username, password);
//
//	}
//
//	/*
//	 if (username.isBlank() || password.isBlank()) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage("username or password cannot be empty");
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else if (username.matches(username_pattern)) {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage("");
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//		*/
//
//	@PostMapping(value = "/logout")
////	@CrossOrigin(origins="http://localhost:8098")
//	@ApiOperation(value = " logout the current session", notes = "Rest Api to logout")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> logout(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		if (session != null) {
//			session.invalidate();
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage("Logout succesfully");
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//		} else {
//			ResponseDto response = new ResponseDto();
//
//			response.setMessage("Already! logout the session");
//			response.setBody(null);
//			response.setStatus(true);
//			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
//
//		}
//
//	}
//
//	@PostMapping("/addUser")
////	@CrossOrigin(origins="http://34.238.255.203:8098")
//	@ApiOperation(value = "add new user ", notes = "This Rest Api add new user to use Recruiter application")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> addUser(HttpServletRequest request,
//			@ApiParam(value = "Enter partner_id", required = false) @RequestParam(defaultValue = "1", required = true) long partner_id,
//			@ApiParam(value = "Enter your name", required = true) @RequestParam(defaultValue = "", required = true) String name,
//			@ApiParam(value = "Enter your strong password", required =true) @RequestParam(defaultValue = "", required = true) String password,
//			@ApiParam(value = "Enter your company", required = true) @RequestParam(defaultValue = "", required = true) String company,
//			@ApiParam(value = "Enter your email id", required = true) @RequestParam(defaultValue = "", required = true) String email,
//			@ApiParam(value = "Enter your designation", required = true) @RequestParam(defaultValue = "", required = true) String designation,
//			@ApiParam(value = "Enter your contact", required = true) @RequestParam(defaultValue = "", required = true) String contact) {
//		User user = new User();
//		user.setPartner_id(partner_id);
//		user.setName(name);
//		user.setPassword(password);
//		user.setDesignation(designation);
//		user.setContact(contact);
//		user.setCompany(company);
//		user.setRole("USER");
//		user.setEmail(email);
//		user.setCreated_by((String) request.getSession().getAttribute("userName"));
//		return user_service.addUser(request, user);
//	}
//
//	@PutMapping("/change_password")
////	@CrossOrigin(origins="http://34.238.255.203:8098")
//	@ApiOperation(value = "change your password", notes = "This Rest Api change the password of user ")
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
//			@ApiResponse(code = 400, message = "Bad request error"),
//			@ApiResponse(code = 401, message = "Unauthorized client error"),
//			@ApiResponse(code = 403, message = "Forbidden client error"),
//			@ApiResponse(code = 404, message = "Invalid data"),
//			@ApiResponse(code = 500, message = "Internal server error") })
//	public ResponseEntity<ResponseDto> change_password(HttpServletRequest request,
//			@ApiParam(value = "Enter your email id", required = false) @RequestParam(defaultValue = "", required = true) String email,
//			@ApiParam(value = "Enter your current password", required = false) @RequestParam(defaultValue = "", required = true) String current_password,
//			@ApiParam(value = "Enter your email id", required = false) @RequestParam(defaultValue = "", required = true) String new_password) {
//
//		return user_service.update_password(request, email, current_password, new_password);
//	}
//
//
//
//
//
//}
