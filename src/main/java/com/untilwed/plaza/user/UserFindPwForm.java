package com.untilwed.plaza.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserFindPwForm {

    @Email
    @NotNull
    private String email;

    @NotNull
    private String id;
}
