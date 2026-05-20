package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final QuestionService questionService;

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("popularPosts",    questionService.getPopularPosts());
        model.addAttribute("recentQuestions", questionService.getRecentQuestions());
        return "main/index";
    }
}