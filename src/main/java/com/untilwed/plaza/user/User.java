package com.untilwed.plaza.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class User {

    //데이터 베이스에서 번호를 붙이기 위한 넘버
    private long number;

    //가입하기위한 최소한의 정보
    @NotBlank
    private String id;
    @NotBlank
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String username;

    boolean emailVerified = false;


}
