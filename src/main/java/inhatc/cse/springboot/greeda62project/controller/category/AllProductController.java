package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AllProductController {

    private final ProductService productService;

    @GetMapping("/allproduct")
    public String allproduct(Model model) {
        List<ProductDTO> products = productService.findAllProducts();
        model.addAttribute("products", products);
        return "/category/allproduct";
    }
}
