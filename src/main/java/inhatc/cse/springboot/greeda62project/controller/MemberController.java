package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dao.MemberDAO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/member")
    public MemberDTO createMember(@RequestBody MemberDTO memberDTO){
        String id = memberDTO.getId();
        String name = memberDTO.getName();
        String email = memberDTO.getEmail();
        String address = memberDTO.getAddress();

        return memberService.saveMember(id, name, email, address);
    }
}
