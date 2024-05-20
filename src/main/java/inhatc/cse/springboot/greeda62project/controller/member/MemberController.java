package inhatc.cse.springboot.greeda62project.controller.member;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String memberSignup() {
        return "member/member";
    }

    @PostMapping("/member")
    public String createMember(MemberDTO memberDTO, Model model){
        String id = memberDTO.getId();
        String password = memberDTO.getPassword();
        String name = memberDTO.getName();
        String email = memberDTO.getEmail();
        String address = memberDTO.getAddress();

        // 아이디 중복 검사
        boolean isDuplicated = memberService.checkIdDuplicated(id);

        // 중복된 경우, null 반환 or 적절한 예외 처리
        if (isDuplicated) {
            model.addAttribute("signUpError", "회원가입에 실패하였습니다(아이디를 다시 확인해주세요.)");
            model.addAttribute("signUpFailed", true);
            return "member/member";
        } else {
            memberService.saveMember(id, password, name, email, address);
            return "member/member";
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
            return "redirect:/";
            } else {
            model.addAttribute("loginError", "아이디나 비밀번호를 확인하세요");
            return "member/member";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/"; // 로그인 페이지나 메인 페이지로 리다이렉트
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
