package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SucculentController {

    private final ProductService productService;

    @GetMapping("/succulent")
    public String succulent(Model model) {
        List<SucculentDTO> succulents = productService.findSucProducts();
        model.addAttribute("succulents", succulents);
        return "/category/succulent";
    }
}
