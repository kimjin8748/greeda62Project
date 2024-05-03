package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PotController {

    private final ProductService productService;
    @GetMapping("/pot")
    public String pot(Model model) {
        List<PotDTO> pots = productService.findPotProducts();
        model.addAttribute("pots", pots);
        return "/category/pot";
    }
}
