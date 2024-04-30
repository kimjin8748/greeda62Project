package inhatc.cse.springboot.greeda62project.controller.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FaqNewController {
    @GetMapping("/faq/new")
    public String faqNew() {
        return "/board/faqNew";
    }
}
