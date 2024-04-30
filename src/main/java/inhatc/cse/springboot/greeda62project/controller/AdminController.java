package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private ProductService productService;
    @GetMapping("/admin")
    public String admin(){
        return "/admin/admin";
    }

//    @GetMapping("/admin/products")
//    public String listProducts(Model model) {
//        model.addAttribute("products", productService.findAll());
//        return "admin/products"; // 상품 목록을 보여주는 View 이름
//    }
}
