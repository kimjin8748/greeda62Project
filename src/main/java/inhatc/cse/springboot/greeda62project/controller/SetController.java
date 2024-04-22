package inhatc.cse.springboot.greeda62project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class SetController {
    @GetMapping("/set")
    public String set() {
        return "set";
    }
}
