package inhatc.cse.springboot.greeda62project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PotController {
    @GetMapping("/pot")
    public String pot() {
        return "pot";
    }
}
