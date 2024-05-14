package inhatc.cse.springboot.greeda62project.controller.mypage;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class MembershipController {

    @GetMapping("/membership")
    public String memberShip(HttpSession session){
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        return "membership";
    }
}
