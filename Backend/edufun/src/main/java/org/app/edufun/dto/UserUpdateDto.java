package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDto {
    private Long user_id;
    private String username;
    private String contact;
    private String email;
    private String password;
    private Integer count;
    private Integer status;
    private String created_by;
    private String dt;
}
