package com.basic.bootbasic4.controller;


import com.basic.bootbasic4.Service.QuestionService;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.dto.QuestionSummaryDto;
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
        model.addAttribute("question", question);
        return "question/detail";
    }

    // 4. 질문 등록 (POST /questions/add)
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("dto") QuestionRequestDto dto,
                      BindingResult bindingResult,
                      Member member,
                      Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", dto.getCategory());
            return "question/question_Form";
        }

        Long id = questionService.addQuestion(dto, member);
        return "redirect:/questions/detail/" + id;
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

    // 7. 검색 + 정렬 + 페이징 (GET /questions?keyword=&category=&petType=&sort=&direction=&page=&size=)
    @GetMapping
    public String list(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String petType,
            @RequestParam(defaultValue = "createdAt") String sort,
            @RequestParam(defaultValue = "desc") String direction,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        if (!ALLOWED_SORT_FIELDS.contains(sort)) {
            sort = "createdAt";
        }
        Sort sortObj = "asc".equalsIgnoreCase(direction)
                ? Sort.by(sort).ascending()
                : Sort.by(sort).descending();

        QuestionCategory categoryEnum = category.isBlank() ? null : QuestionCategory.valueOf(category.toUpperCase());
        QuestionPetType petTypeEnum = petType.isBlank() ? null : QuestionPetType.valueOf(petType.toUpperCase());

        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<QuestionSummaryDto> questions = questionService.search(keyword, categoryEnum, petTypeEnum, pageable);

        model.addAttribute("questions", questions);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", categoryEnum);
        model.addAttribute("petType", petTypeEnum);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("categories", QuestionCategory.values());
        model.addAttribute("petTypes", QuestionPetType.values());

        return "question/list";
    }

}
