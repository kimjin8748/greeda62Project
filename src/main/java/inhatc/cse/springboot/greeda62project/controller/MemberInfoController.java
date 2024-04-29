package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

}
