package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dao.MemberDAO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.validator.MemberValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
public class MemberController {

    private MemberValidator memberValidator;
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

    @PostMapping("/member/login-check")
    public ResponseEntity<String> createMember(@RequestBody MemberDTO memberDTO, BindingResult bindingResult) {
        // DTO를 Validator에 전달하여 유효성 검사 수행
        memberValidator.validate(memberDTO, bindingResult);

        // 검증 결과 확인
        if (bindingResult.hasErrors()) {
            // 오류가 있는 경우
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            return ResponseEntity.ok("회원 가입 성공");
        }
    }
//    @GetMapping("/member/{id}")
//    public ResponseEntity<String> checkDuplicateId(@PathVariable String id){
//        boolean isDuplicate = memberService.checkDuplicateId(id);
//        if (isDuplicate) {
//            return ResponseEntity.ok("중복된 아이디입니다.");
//        } else {
//            return ResponseEntity.ok("사용 가능한 아이디입니다.");
//        }
//    }
}
