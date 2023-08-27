package com.untilwed.plaza.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Data
public class DeleteUserForm {

    @NotBlank
    private String password;
}
