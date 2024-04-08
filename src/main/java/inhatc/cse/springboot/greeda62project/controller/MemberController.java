package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dao.MemberDAO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public String memberSignup() {
        return "member";
    }

    @PostMapping("/member")
    public MemberDTO createMember(MemberDTO memberDTO){
        String id = memberDTO.getId();
        String password = memberDTO.getPassword();
        String name = memberDTO.getName();
        String email = memberDTO.getEmail();
        String address = memberDTO.getAddress();

        return memberService.saveMember(id, password, name, email, address);
    }

    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password, HttpSession session){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setPassword(password);

        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            session.setAttribute("id", loginResult.getId());
            return "index";
        } else {
            return "member";
        }
    }
}
