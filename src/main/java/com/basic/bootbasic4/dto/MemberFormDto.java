package com.basic.bootbasic4.dto;

import lombok.Data;

@Data
public class MemberFormDto {
    private String username;
    private String password;
    private String confirmPassword;
    private String nickname;
    private String email;
    private String petType;
    private Integer petStarted;
}
