package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public String member() {
        return "member";
    }
}
