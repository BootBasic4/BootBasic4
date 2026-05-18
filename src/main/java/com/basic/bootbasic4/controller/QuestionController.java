package com.basic.bootbasic4.controller;


import com.basic.bootbasic4.Service.AnswerService;
import com.basic.bootbasic4.Service.QuestionService;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.entity.Answer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    // 답변 내역 출력을 위함
    private final AnswerService answerService;

    // 1. 게시판별 전체 조회 (GET /questions/{category})
    @GetMapping("/{category}")
    public String list(@PathVariable String category,
                       @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        Page<QuestionResponseDto> list = questionService.getListByCategory(category, "ALL", pageable);
        model.addAttribute("questions", list);
        model.addAttribute("category", category);
        return "question/list";
    }

    // 2. 타입별 필터링 조회 (GET /questions/{category}/{pet_type})
    @GetMapping("/{category}/{pet_type}")
    public String filteredList(@PathVariable String category,
                               @PathVariable String pet_type,
                               @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                               Model model) {
        Page<QuestionResponseDto> list = questionService.getListByCategory(category, pet_type, pageable);
        model.addAttribute("questions", list);
        model.addAttribute("category", category);
        model.addAttribute("petType", pet_type);
        return "question/list";
    }

    // 3. 상세 조회 (GET /questions/{question_id})
    @GetMapping("/detail/{question_id}")
    public String detail(@PathVariable("question_id") Long id, Model model) {
        QuestionResponseDto question = questionService.getQuestionDetail(id);

        // 답변 내역들 붙인 코드
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        //


        model.addAttribute("question", question);


        // 추가함
        model.addAttribute("answers", answers);
        //

        return "question/detail";
    }

    // (추가) 질문 등록 폼으로 이동 (GET /questions/add)
    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("dto", new QuestionRequestDto());

        return "question/question_form";
    }

    // 4. 질문 등록 (POST /questions/add)
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("dto") QuestionRequestDto dto,
                      BindingResult bindingResult,
                      Member member,
                      Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", dto.getCategory());
            return "question/question_form";
        }

        Long id = questionService.addQuestion(dto, member);
        return "redirect:/questions/detail/" + id;
    }

    // (추가) @GetMapping {question_form.html}
    @GetMapping("/edit/{question_id}")
    public String editForm(@PathVariable("question_id") Long id, Model model) {
        QuestionResponseDto dto = questionService.getQuestionDetail(id);
        model.addAttribute("dto", dto);
        model.addAttribute("questionId", id);
        return "question/question_form";
    }


    // 5. 질문 수정 (POST /questions/edit/{question_id})
    @PostMapping("/edit/{question_id}")
    public String edit(@PathVariable("question_id") Long id,
                       @Valid @ModelAttribute("dto") QuestionRequestDto dto,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("questionId", id);
            model.addAttribute("category", dto.getCategory());
            return "question/question_form";
        }

        questionService.updateQuestion(id, dto);
        return "redirect:/questions/detail/" + id;
    }

    // 6. 질문 삭제 (POST /questions/delete/{question_id})
    @PostMapping("/delete/{question_id}")
    public String delete(@PathVariable("question_id") Long id, @RequestParam String category) {
        questionService.deleteQuestion(id);
        return "redirect:/questions/" + category;
    }

    // 7. 질문 통합 검색 (GET /questions/search)
    @GetMapping("/search")
    public String search(@RequestParam String keyword,
                         @PageableDefault(size = 10) Pageable pageable,
                         Model model) {
        Page<QuestionResponseDto> searchList = questionService.searchAll(keyword, pageable);
        model.addAttribute("questions", searchList);
        return "question/list";
    }


}
