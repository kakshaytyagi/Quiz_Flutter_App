package org.app.edufun.validation;

import org.apache.commons.lang3.StringUtils;
import org.app.edufun.dao.UserRepository;
import org.app.edufun.dto.ResponseDto;
import org.app.edufun.dto.UserCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserValidation {
    @Autowired
    private UserRepository userRepository;

    public ResponseDto addUser(UserCreateDto userCreateDto) {

        if (StringUtils.isBlank(userCreateDto.getUsername())) {
            return ResponseDto.builder().status(false).message("Name is mandatory!").build();
        }  else if (StringUtils.isBlank(userCreateDto.getEmail())) {
            return ResponseDto.builder().status(false).message("Email is mandatory!").build();
        } else if (StringUtils.isBlank(userCreateDto.getPassword())) {
            return ResponseDto.builder().status(false).message("Password is mandatory!").build();
        } else if (userRepository.isRecordExist(userCreateDto.getEmail(), userCreateDto.getContact())) {
            return ResponseDto.builder().status(false).message("Duplicate entry is not allowed !").build();
        } else {
            return ResponseDto.builder().status(true).build();
        }
    }
}
