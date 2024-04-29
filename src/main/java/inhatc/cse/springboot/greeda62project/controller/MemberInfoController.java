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
    public String updateMember(@ModelAttribute MemberDTO memberDTO, HttpSession session, RedirectAttributes redirectAttributes) {
        String id = memberDTO.getId();
        String password = memberDTO.getPassword();
        String name = memberDTO.getName();
        String email = memberDTO.getEmail();
        String address = memberDTO.getAddress();

        String sessionId = (String) session.getAttribute("id");
        if (sessionId == null || !sessionId.equals(memberDTO.getId())) {
            // User is not logged in or trying to edit another user's information
            return "redirect:/member";
        }

        boolean updateResult = memberService.updateMember(id, password, name, email, address);
        if (updateResult) {
            redirectAttributes.addFlashAttribute("successMessage", "회원 정보가 성공적으로 수정되었습니다.");
            return "redirect:/modify";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "회원 정보 수정에 실패하였습니다.");
            return "redirect:/modify";
        }
    }


}
