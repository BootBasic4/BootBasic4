package com.basic.bootbasic4.dto;

import lombok.Data;

@Data
public class MyPageFormDto {
    private String nickname;
    private String email;

    private String currentPassword;
    private String newPassword;
}
