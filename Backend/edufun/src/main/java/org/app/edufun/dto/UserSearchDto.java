package org.app.edufun.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Jacksonized
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UserSearchDto {
    private Long user_id;
    private String name;
    private String contact;
    private String email;
    private String password;
    private String creator_by;
    private String dt;
    private Integer status;

}
