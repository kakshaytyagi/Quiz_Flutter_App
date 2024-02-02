package org.app.edufun.service;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.entity.UserMaster;

public interface UserMasterService {

	public Object getUser(String email);

	
	public ResponseEntity<ResponseDto> addUser(HttpSession  session,String token,String name,String password ,String email,String contact);
}
