package inhatc.cse.springboot.greeda62project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @RequestMapping(value="/html2", method = RequestMethod.GET)
    public String api_test(){
        return "index";
    }
}
