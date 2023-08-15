package com.untilwed.plaza.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;

@Data
public class LoginForm {
    @NotBlank
    private String id;

    @NotBlank
    private String password;
}
