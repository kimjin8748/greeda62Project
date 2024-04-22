package inhatc.cse.springboot.greeda62project.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class PotController {
    @GetMapping("/pot")
    public String pot() {
        return "pot";
    }
}
