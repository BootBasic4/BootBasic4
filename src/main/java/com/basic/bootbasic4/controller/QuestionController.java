package com.basic.bootbasic4.controller;


import com.basic.bootbasic4.Repository.ReportRepository;
import com.basic.bootbasic4.Service.AnswerService;
import com.basic.bootbasic4.Service.QuestionService;
import com.basic.bootbasic4.dto.AnswerFormDto;
import com.basic.bootbasic4.dto.QuestionRequestDto;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.dto.QuestionSummaryDto;
import com.basic.bootbasic4.entity.QuestionCategory;
import com.basic.bootbasic4.entity.QuestionPetType;
import com.basic.bootbasic4.entity.Answer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.basic.bootbasic4.entity.*;
import org.springframework.security.core.userdetails.User;

import java.util.Set;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of("createdAt", "viewCount", "updatedAt");

    private final QuestionService questionService;
    // 답변 내역 출력을 위함
    private final AnswerService answerService;
    // 신고 기능을 위한 추가코드
    private final ReportRepository reportRepository;


    // 3. 상세 조회 (GET /questions/{question_id})
    @GetMapping("/detail/{question_id}")
    public String detail(@PathVariable("question_id") Long id, Model model) {
        QuestionResponseDto question = questionService.getQuestionDetail(id);

        // 답변 내역들 붙인 코드
        List<Answer> answers = answerService.getAnswersByQuestionId(id);
        //

        // 현재 게시글이 신고 처리중(PENDING) 상태인지 확인
        boolean isReported =
                reportRepository.existsByQuestion_IdAndStatus(id, "PENDING");

        // 신고 처리중(PENDING)인 댓글 번호 목록 조회
        List<Long> reportedAnswerIds = answers.stream()
                .filter(answer -> reportRepository.existsByAnswer_AnswerIdAndStatus(answer.getAnswerId(), "PENDING"))
                .map(Answer::getAnswerId)
                .toList();

        model.addAttribute("question", question);
        model.addAttribute("isReported", isReported); // 추가
        model.addAttribute("reportedAnswerIds", reportedAnswerIds); // 추가

        // 추가함
        model.addAttribute("answers", answers);
        model.addAttribute("answerFormDto", new AnswerFormDto());
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
                      @AuthenticationPrincipal User user,
                      Model model) {

        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("category", dto.getCategory());
            return "question/question_form";
        }

        Long id = questionService.addQuestion(dto, user.getUsername());
        return "redirect:/questions/detail/" + id;
    }


    // (추가) @GetMapping {question_form.html}
    @GetMapping("/edit/{question_id}")
    public String editForm(@PathVariable("question_id") Long id, Model model) {

        QuestionResponseDto responseDto = questionService.getQuestionDetail(id);
        QuestionRequestDto requestDto = new QuestionRequestDto();


        requestDto.setTitle(responseDto.getTitle());
        requestDto.setContent(responseDto.getContent());
        requestDto.setImageUrl(responseDto.getImageUrl());

        if (responseDto.getCategory() != null) {
            requestDto.setCategory(QuestionCategory.valueOf(responseDto.getCategory().toString().toUpperCase()));
        }

        if (responseDto.getPetType() != null) {
            requestDto.setPetType(QuestionPetType.valueOf(responseDto.getPetType().toString().toUpperCase()));
        }

        model.addAttribute("dto", requestDto);
        model.addAttribute("questionId", id);

        return "question/question_form";
    }


    // 5. 질문 수정 (POST /questions/edit/{question_id})
    @PostMapping("/edit/{question_id}")
    public String edit(@PathVariable("question_id") Long id,
                       @Valid @ModelAttribute("dto") QuestionRequestDto dto,
                       BindingResult bindingResult,
                       @AuthenticationPrincipal User user,
                       Model model) {

        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("questionId", id);
            model.addAttribute("category", dto.getCategory());
            return "question/question_form";
        }

        questionService.updateQuestion(id, dto, user.getUsername());
        return "redirect:/questions/detail/" + id;
    }

    // 6. 질문 삭제 (POST /questions/delete/{question_id})
    @PostMapping("/delete/{question_id}")
    public String delete(@PathVariable("question_id") Long id,
                         @RequestParam String category,
                         @AuthenticationPrincipal User user) {

        if (user == null) {
            throw new IllegalArgumentException("로그인이 필요한 서비스입니다.");
        }

        questionService.deleteQuestion(id, user.getUsername());
        return "redirect:/questions/" + category;
    }

    // 7. 검색 + 정렬 + 페이징 (GET /questions?keyword=&category=&petType=&sort=&direction=&page=&size=)
    @GetMapping("/{category}")
    public String list(
            @PathVariable String category,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "TITLE_CONTENT") String searchType,
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
        Page<QuestionSummaryDto> questions = questionService.search(keyword, searchType, categoryEnum, petTypeEnum, pageable);

        model.addAttribute("questions", questions);
        model.addAttribute("category", category.toUpperCase());
        model.addAttribute("categoryEnum", categoryEnum);
        model.addAttribute("petType", petType.toUpperCase());
        model.addAttribute("keyword", keyword);
        model.addAttribute("searchType", searchType);
        model.addAttribute("sort", sort);
        model.addAttribute("direction", direction);
        model.addAttribute("categories", QuestionCategory.values());
        model.addAttribute("petTypes", QuestionPetType.values());

        return "question/list";
    }
}
