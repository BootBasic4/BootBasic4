package com.basic.bootbasic4.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemberFormDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]{4,20}$",
            message = "아이디는 영문, 숫자, . _ - 포함 4~20자만 가능합니다."
    )
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String confirmPassword;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9_]{2,20}$",
            message = "닉네임은 한글, 영문, 숫자, _ 포함 2~20자만 가능합니다."
    )
    private String nickname;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    private String petType;

    private Integer petStarted;
}
