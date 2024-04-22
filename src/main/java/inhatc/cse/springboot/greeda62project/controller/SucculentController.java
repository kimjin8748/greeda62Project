package inhatc.cse.springboot.greeda62project.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class SucculentController {
    @GetMapping("/succulent")
    public String succulent() {
        return "succulent";
    }
}
