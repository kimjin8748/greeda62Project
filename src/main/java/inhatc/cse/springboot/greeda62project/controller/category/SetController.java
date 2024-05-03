package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SetController {

    private final ProductService productService;
    @GetMapping("/set")
    public String set(Model model) {
        List<SetDTO> sets = productService.findSetProducts();
        model.addAttribute("sets", sets);
        return "/category/set";
    }
}
