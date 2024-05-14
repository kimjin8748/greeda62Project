package inhatc.cse.springboot.greeda62project.controller.member;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ForgotController {

    private final MemberService memberService;

    @GetMapping("/forgot")
    public String find() {
        return "/forgot";
    }

    @PostMapping("/forgot")
    public String forgot(@RequestParam("name") String name, @RequestParam("email") String email, HttpSession session, Model model){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setName(name);
        memberDTO.setEmail(email);

        MemberDTO loginResult = memberService.forgot(memberDTO);
        if(loginResult != null) {
            session.setAttribute("id", loginResult.getId());
            model.addAttribute("memberId", loginResult.getId());
            model.addAttribute("forgotsuccess", "찾으시는 아이디는 " + loginResult.getId() + " 입니다.");
            return "/member";
        } else {
            model.addAttribute("forgoterror", "가입된 아이디가 없습니다.");
            return "/forgot";
        }
    }
}
