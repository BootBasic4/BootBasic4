package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.MemberService;
import com.basic.bootbasic4.Service.QuestionService;
import com.basic.bootbasic4.dto.QuestionResponseDto;
import com.basic.bootbasic4.entity.Answer;
import com.basic.bootbasic4.entity.Member;
import com.basic.bootbasic4.entity.Question;
import com.basic.bootbasic4.exception.ErrorCode;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import com.basic.bootbasic4.Service.AnswerService;
import com.basic.bootbasic4.dto.AnswerFormDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;


@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final MemberService memberService;


    // 답변등록 //
    // 답변 내용을 html 에서 작성 -> api 요청
    // -> 세션에서 유저정보를 꺼냄
    // -> 질문, 유저, 답변 내용(dto)을 서비스에 넘김(이후 db에 저장하고)
    // -> 해당 질문 화면으로 리다이렉트
    @PostMapping("/{questionId}")
    public String addAnswer(@PathVariable Long questionId, @Valid @ModelAttribute("answerFormDto")  AnswerFormDto answerFormDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        // 답변 내용 비어있을경우
        if (bindingResult.hasErrors()) {
            QuestionResponseDto question = questionService.getQuestionDetail(questionId);
            List<Answer> answers = answerService.getAnswersByQuestionId(questionId);
            model.addAttribute("question", question);
            model.addAttribute("answers", answers);
            return "question/detail";
        }

        // questionService로 Qeustion 객체 가져와서 answerService에 넘기기
        Question question = questionService.getQuestion(questionId);

        // memberService로 member 객체 가져와서 answerService에 넘기기
        Member member = memberService.getMemberByUsername(userDetails.getUsername());

        answerService.create(question, member, answerFormDto);
        return "redirect:/questions/detail/"+questionId;
    }

    // 답변수정 화면 get//
    // 답변id는 경로변수, 질문id는 쿼리변수
    // 답변수정클릭 -> 답변수정화면으로
    @GetMapping("/edit/{answerId}")
    public String editAnswerForm(@PathVariable Long answerId, @RequestParam Long questionId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // TODO: 작성자 본인인지 확인하는 코드

        // if (!answer.getMember().getUsername().equals(userDetails.getUsername())) {
        //    throw new AccessDeniedException(ErrorCode.ANSWER_UNAUTHORIZED.getMessage());
        //}

        Answer answer = answerService.findById(answerId).orElseThrow(
                ()->new NoSuchElementException(ErrorCode.ANSWER_NOT_FOUND.getMessage())
        );

        AnswerFormDto answerFormDto = new AnswerFormDto();
        answerFormDto.setContent(answer.getContent());

        model.addAttribute("answer", answer);
        model.addAttribute("answerFormDto", answerFormDto);
        // 아래 빠져서 답변 수정 후 원래 게시글 화면으로 안 돌아갔었음 ㅠ
        model.addAttribute("questionId", questionId);

        return "answer/edit";
    }

    // 답변수정 //
    // 답변을 수정하면 -> 다시 해당 질문 화면으로 돌아감(
    // 수정할 질문 id 경로 변수로 받고, 리다이렉트를 위한 질문 id는 쿼리변수
    @PostMapping("/edit/{answerId}")
    public String editAnswer(@PathVariable Long answerId, @RequestParam Long questionId, @Valid @ModelAttribute("answerFormDto") AnswerFormDto answerFormDto, BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (bindingResult.hasErrors()) {
            Answer answer = answerService.findById(answerId)
                    .orElseThrow(() -> new NoSuchElementException(ErrorCode.ANSWER_NOT_FOUND.getMessage()));
            model.addAttribute("answer", answer);
            model.addAttribute("questionId", questionId);
            return "answer/edit";
        }

//        System.out.println("answerId: " + answerId);
//        System.out.println("questionId: " + questionId);
//        System.out.println("content: " + answerFormDto.getContent());

        answerService.edit(answerId, answerFormDto);
        return "redirect:/questions/detail/"+questionId;
    }

    // 답변삭제 //
    // 답변id는 경로변수, 질문id는 쿼리변수
    // 답변삭제클릭-> api 요청
    // -> 작성자인지확인
    // -> 서비스에 삭제 요청
    @PostMapping("/delete/{answerId}")
    public String deleteAnswer(@PathVariable Long answerId, @RequestParam Long questionId, @AuthenticationPrincipal UserDetails userDetails) {
        // TODO: 세션의 유저가 작성자 본인인지 확인
        // if (!answer.getMember().getUsername().equals(userDetails.getUsername())) {
        //    throw new AccessDeniedException(ErrorCode.ANSWER_UNAUTHORIZED.getMessage());
        //}

        answerService.delete(answerId);
        return "redirect:/questions/detail/"+questionId;
    }

}
