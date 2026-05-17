package com.basic.bootbasic4.controller;

import com.basic.bootbasic4.Service.ReportService;
import com.basic.bootbasic4.dto.ReportRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    // 1. 게시글 신고
    @PostMapping("/questions/{questionId}/report")
    public String reportQuestion(@PathVariable Long questionId, @ModelAttribute ReportRequestDto dto, Authentication authentication) {
        String username = authentication.getName();
        reportService.reportQuestion(questionId, username, dto.getReason());

        return "redirect:/questions/detail/" + questionId;
    }

    // 2. 답변 신고
    @PostMapping("/answers/{answerId}/report")
    public String reportAnswer(@PathVariable Long answerId, @ModelAttribute ReportRequestDto dto, Authentication authentication) {
        String username = authentication.getName();
        reportService.reportAnswer(answerId, username, dto.getReason());

        return "redirect:/";
    }


    // 3. 전체 신고 목록 조회
    @GetMapping("/admin/reports")
    public String getAllReports(Model model) {
        model.addAttribute("reports", reportService.getAllReports());
        return "member/report";
    }

    // 4. 신고 승인 처리
    @PostMapping("/admin/reports/{reportId}/delete")
    public String approveReport(@PathVariable Long reportId) {
        reportService.approveReport(reportId);
        return "redirect:/admin/reports";
    }

    // 5. 신고 반려 처리
    @PostMapping("/admin/reports/{reportId}/reject")
    public String rejectReport(@PathVariable Long reportId) {
        reportService.rejectReport(reportId);
        return "redirect:/admin/reports";
    }

}
