package inhatc.cse.springboot.greeda62project.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class SetController {
    @GetMapping("/set")
    public String set() {
        return "set";
    }
}
