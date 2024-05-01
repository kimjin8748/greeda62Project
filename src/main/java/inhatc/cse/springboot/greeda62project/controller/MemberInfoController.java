package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MemberInfoController {

    MemberService memberService;

    @Autowired
    public MemberInfoController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/modify")
    public String editMemberForm(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }

        MemberDTO memberDTO = memberService.findMemberById(id);
        if (memberDTO != null) {
            model.addAttribute("member", memberDTO);
            return "/modify";
        } else {
            // Member not found, handle accordingly
            return "redirect:/";
        }
    }

    @PostMapping("/modify")
    public String updateMember(@ModelAttribute MemberDTO memberDTO,
                               @RequestParam("action") String action, HttpSession session,
                               RedirectAttributes redirectAttributes) {
        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null || !sessionId.equals(memberDTO.getId())) {
            // 사용자가 로그인하지 않았거나 다른 사용자의 정보를 수정하려고 시도하는 경우
            return "redirect:/member";
        }

        if ("update".equals(action)) {
            // 회원 정보 수정 로직
            boolean updateResult = memberService.updateMember(memberDTO.getId(), memberDTO.getPassword(), memberDTO.getName(), memberDTO.getEmail(), memberDTO.getAddress());
            if (updateResult) {
                redirectAttributes.addFlashAttribute("updateSuccess", "회원 정보가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("updateError", "회원 정보 수정에 실패하였습니다.");
            }
        } else if ("delete".equals(action)) {
            // 회원 탈퇴 로직
            boolean deleteResult = memberService.deleteMember(memberDTO.getId());
            if (deleteResult) {
                session.invalidate(); // 세션 무효화
                redirectAttributes.addFlashAttribute("deleteSuccess", "회원 탈퇴가 성공적으로 처리되었습니다.");
                return "redirect:/member";
            } else {
                redirectAttributes.addFlashAttribute("deleteError", "회원 탈퇴 처리에 실패하였습니다.");
            }
        }
        return "redirect:/modify";
    }
}
