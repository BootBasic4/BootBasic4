package com.basic.bootbasic4.controller;

import ch.qos.logback.core.model.Model;
import com.basic.bootbasic4.Service.AnswerService;
import com.basic.bootbasic4.dto.AnswerFormDto;
import com.basic.bootbasic4.entity.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private AnswerService answerService;

//    @GetMapping("/{question_id}")
//    public String showAnswerForm(@PathVariable String question_id) {
//        return "/answer/showAnswerForm";
//    }

    // 답변등록 //
    // 답변 내용을 html 에서 작성 -> api 요청
    // -> 해당 내용(dto)을 서비스에 넘김(이후 db에 저장하고)
    // -> 해당 질문 화면으로 리다이렉트
    @PostMapping("/{question_id}")
    public String addAnswer(@PathVariable("question_id") Long questionId, AnswerFormDto answerFormDto) {
        answerService.save(questionId, answerFormDto);
        return "redirect:/question/"+questionId;
    }


    // 답변조회 //
    // 특정 게시글을 누르면 해당 게시글의 모든 답변 반환
    @GetMapping("/{question_id}")
    public String findAnswer(@PathVariable("question_id") Long questionId, Model model) {
        List<Answer> answerList = answerService.getAnswerByQuestionId(questionId);
        model.addAttribute("answerList", answerList); // question_id 에 해당하는 답변 내역들
        model.addAttribute("question_id", questionId); // quesiton_id
        return "question/detail";
    }


    // 답변수정 //
    // 답변을 수정하면 -> 다시 해당 질문 화면으로 돌아감
    @PostMapping("/edit/{answer_id}")
    public String editAnswer(@PathVariable("answer_id") Long answerId, AnswerFormDto answerFormDto) {
        // 서비스에서 질문 id 리턴해주기
        Long questionId = answerService.edit(answerId, answerFormDto);
        return "redirect:/question/"+questionId;
    }

    // 답변삭제 //
    @PostMapping("/delete/{answer_id}")
    public String deleteAnswer(@PathVariable("answer_id") Long answerId) {
        Long questionId=answerService.delete(answerId);
        return "redirect:/question/"+answerId;
    }

}
