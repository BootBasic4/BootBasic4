package com.basic.bootbasic4.exception;

import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 존재하지 않는 리소스
    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElement(NoSuchElementException e, Model model) {

        model.addAttribute("errorCode", ErrorCode.QUESTION_NOT_FOUND.getCode());
        model.addAttribute("errorMessage", e.getMessage());

        return "error/404";
    }

    // 잘못된 입력
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException e, Model model) {

        model.addAttribute("errorCode", ErrorCode.INVALID_INPUT.getCode());
        model.addAttribute("errorMessage", e.getMessage());

        return "error/400";
    }

    // 권한 없음
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Model model) {

        model.addAttribute("errorCode", ErrorCode.MEMBER_NOT_LOGGED_IN.getCode());
        model.addAttribute("errorMessage", ErrorCode.MEMBER_NOT_LOGGED_IN.getMessage());

        return "error/403";
    }

    // 유효성 검증 실패
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidation(MethodArgumentNotValidException e, Model model) {

        String errorMessage = e.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .findFirst()
                .orElse(ErrorCode.INVALID_INPUT.getMessage());

        model.addAttribute("errorCode", ErrorCode.INVALID_INPUT.getCode());
        model.addAttribute("errorMessage", errorMessage);

        return "error/400";
    }

    // 그 외 모든 예외
    @ExceptionHandler(Exception.class)
    public String handleException(Model model) {

        model.addAttribute("errorCode", ErrorCode.INTERNAL_SERVER_ERROR.getCode());
        model.addAttribute("errorMessage", ErrorCode.INTERNAL_SERVER_ERROR.getMessage());

        return "error/500";
    }
}