package com.basic.bootbasic4.controller;


import com.basic.bootbasic4.Service.QuestionService;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.dto.QuestionSummaryDto;
import com.basic.bootbasic4.entity.Member;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("createdAt", "viewCount", "updatedAt");

    private final QuestionService questionService;


    // 3. 상세 조회 (GET /questions/{question_id})
    @GetMapping("/detail/{question_id}")
    public String detail(@PathVariable("question_id") Long id, Model model) {
        QuestionResponseDto question = questionService.getQuestionDetail(id);
        model.addAttribute("question", question);
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
                       Member member,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("questionId", id);
            model.addAttribute("category", dto.getCategory());
            return "question/question_form";
        }

        questionService.updateQuestion(id, dto, member);
        return "redirect:/questions/detail/" + id;
    }

    // 6. 질문 삭제 (POST /questions/delete/{question_id})
    @PostMapping("/delete/{question_id}")
    public String delete(@PathVariable("question_id") Long id, @RequestParam String category, Member member) {
        questionService.deleteQuestion(id, member);
        return "redirect:/questions/" + category;
    }

    // 7. 검색 + 정렬 + 페이징 (GET /questions?keyword=&category=&petType=&sort=&direction=&page=&size=)
    @GetMapping("/{category}")
    public String list(
            @PathVariable String category,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "ALL") String petType,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {

        if (!ALLOWED_SORT_FIELDS.contains(sort)) { sort = "createdAt"; }
        Sort sortObj = "asc".equalsIgnoreCase(direction) ? Sort.by(sort).ascending() : Sort.by(sort).descending();

        // 카테고리 변환 (PathVariable 활용)
        QuestionCategory categoryEnum = QuestionCategory.valueOf(category.toUpperCase());

        // 펫타입 변환 (로직이 ALL을 걸러주므로 그대로 변환만 하면 됨)
        // petType이 "ALL"이면 categoryEnum은 QuestionPetType.ALL이 되고,
        // Specification이 이를 인지해서 쿼리에서 제외
        QuestionPetType petTypeEnum = QuestionPetType.valueOf(petType.toUpperCase());

        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<QuestionSummaryDto> questions = questionService.search(keyword, categoryEnum, petTypeEnum, pageable);


        model.addAttribute("questions", questions);
        model.addAttribute("category", category.toUpperCase());
        model.addAttribute("petType", petType.toUpperCase());
        model.addAttribute("keyword", keyword);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("categories", QuestionCategory.values());
        model.addAttribute("petTypes", QuestionPetType.values());

        return "question/list";
    }

}
