package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String email;
    private String contact;
    private String firstname;
    private String lastname;
    private Integer question;
    private String dob;
    private String updated_at;
}
