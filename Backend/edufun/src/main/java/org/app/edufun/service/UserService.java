package org.app.edufun.service;

import lombok.AllArgsConstructor;
import org.app.edufun.dao.UserRepository;
import org.app.edufun.dto.*;
import org.app.edufun.entity.User;
import org.app.edufun.validation.UserValidation;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private UserRepository userRepository;
    private final UserValidation userValidation;

    public ResponseDto addUser(UserCreateDto userCreateDto) {
        ResponseDto response = userValidation.addUser(userCreateDto);
        if (!response.isStatus()) {
            return response;
        }

        User user = User.builder()
                .username(userCreateDto.getUsername())
                .firstname(null)
                .lastname(null)
                .password(userCreateDto.getPassword())
                .contact(userCreateDto.getContact())
                .email(userCreateDto.getEmail())
                .avatar("assets/avatar/Avatar2.png")
                .question(0)
                .dob(null)
                .created_at(Instant.now())
                .created_by(userCreateDto.getUsername())
                .updated_at(null)
                .status(1)
                .build();

        boolean isInserted = userRepository.saveUser(user);

        if (isInserted) {
            return ResponseDto.builder().message("User added successfully!").status(true).body(user).build();
        } else {
            return ResponseDto.builder().message("New User is not created!").status(false).body(null).build();
        }
    }


    public ResponseDto searchUser(HttpSession session, UserSearchDto userSearchDto) {
        try {
            List<User> list = userRepository.searchUser(session, userSearchDto);
            if (list == null || list.isEmpty()) {
                return ResponseDto.builder().message("Sorry! Data not found !").status(false).body(null).build();
            } else {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body(list).build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }

    }

    public ResponseDto updateUser(HttpSession session, UserUpdateDto userUpdateDto) {
        try {
            if (userRepository.updateUser(session, userUpdateDto) > 0) {
                return ResponseDto.builder().message("Congrats ! The request has been executed.").status(true).body("User has been updated").build();
            } else {
                return ResponseDto.builder().message("Sorry! The request has not been executed.").status(false).body("User has not been updated").build();
            }
        } catch (Exception e) {
            return ResponseDto.builder().message("Internal server error !").status(false).body(null).build();
        }
    }

    public ResponseDto getById(long user_id) {
        try {
            User list = userRepository.searchById(user_id);
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


