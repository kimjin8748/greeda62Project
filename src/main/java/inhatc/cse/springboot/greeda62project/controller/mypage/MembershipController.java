package inhatc.cse.springboot.greeda62project.controller.mypage;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MembershipController {

    @GetMapping("/membership")
    public String memberShip(HttpSession session){
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        return "mypage/membership";
    }
}
