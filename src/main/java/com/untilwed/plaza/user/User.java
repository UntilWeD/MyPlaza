package com.untilwed.plaza.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Data
public class User {

    //데이터 베이스에서 번호를 붙이기 위한 넘버
    private long number;

    //가입하기위한 최소한의 정보
    private String id;
    private String password;
    private String email;

    private String username;


}
