package com.untilwed.plaza.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserFindForm {
    @NotNull
    String email;

    String id;
}
