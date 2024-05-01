package inhatc.cse.springboot.greeda62project.controller.product;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductDetail {

    @GetMapping("/productdetail")
    public String productdetail() {
        return "/product/productdetail";
    }
}
