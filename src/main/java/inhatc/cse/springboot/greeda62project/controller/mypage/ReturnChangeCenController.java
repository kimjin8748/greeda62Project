package inhatc.cse.springboot.greeda62project.controller.mypage;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReturnChangeCenController {


    @GetMapping("/return_change_cen")
    public String delivery(HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        return "mypage/return_change_cen";
    }
}

