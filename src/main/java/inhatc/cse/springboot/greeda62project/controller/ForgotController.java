package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForgotController {

    private MemberService memberService;

    @Autowired
    public ForgotController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/forgot")
    public String find() {
        return "forgot";
    }

    @PostMapping("/forgot")
    public String forgot(@RequestParam("name") String name, @RequestParam("email") String email, HttpSession session, Model model){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName(name);
        memberDTO.setEmail(email);

        MemberDTO loginResult = memberService.forgot(memberDTO);
        if(loginResult != null) {
            session.setAttribute("name", loginResult.getName());
            return "main";
        } else {
            model.addAttribute("loginError", "아이디나 비밀번호를 확인하세요");
            return "forgot";
        }
    }
}
