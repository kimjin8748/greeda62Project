package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public MemberDTO createMember(MemberDTO memberDTO, Model model){
        String id = memberDTO.getId();
        String password = memberDTO.getPassword();
        String name = memberDTO.getName();
        String email = memberDTO.getEmail();
        String address = memberDTO.getAddress();

        // 아이디 중복 검사
        boolean isDuplicated = memberService.checkIdDuplicated(id);

        // 중복된 경우, null 반환 or 적절한 예외 처리
        if (isDuplicated) {
            model.addAttribute("signUpError", "아이디가 이미 있습니다.");
            model.addAttribute("signUpFailed", true);
            return null;
        } else {
            return memberService.saveMember(id, password, name, email, address);
        }
    }


    @PostMapping("/login")
    public String login(@RequestParam("id") String id, @RequestParam("password") String password, HttpSession session, Model model){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(id);
        memberDTO.setPassword(password);

        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null) {
            session.setAttribute("id", loginResult.getId());
            return "main";
        } else {
            model.addAttribute("loginError", "아이디나 비밀번호를 확인하세요");
            return "member";
        }
    }

    @PostMapping("/check-id")
    @ResponseBody // 비동기 요청에 대한 응답을 JSON 등의 형태로 반환하기 위해 사용
    public Map<String, Boolean> checkId(@RequestParam("id") String id) {
        Map<String, Boolean> response = new HashMap<>();
        boolean isDuplicated = memberService.checkIdDuplicated(id); // ID 중복 검사 로직 구현
        response.put("isDuplicated", isDuplicated);
        return response;
    }


}
