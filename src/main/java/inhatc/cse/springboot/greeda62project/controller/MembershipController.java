package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
public class MembershipController {

    @GetMapping("/membership")
    public String memberShip(){
        return "membership";
    }
}
