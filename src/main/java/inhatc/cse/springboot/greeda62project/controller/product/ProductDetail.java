package inhatc.cse.springboot.greeda62project.controller.product;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ProductDetail {

    private final ProductService productService;

    @GetMapping("/productdetail")
    public String showProductDetail(@RequestParam("id") String serialNumber, Model model) {
        PotDTO pot = productService.findPotById(serialNumber);
        SucculentDTO succulent = productService.findSucculentById(serialNumber);
        SetDTO set = productService.findSetById(serialNumber);

        model.addAttribute("pot", pot);
        model.addAttribute("succulent", succulent);
        model.addAttribute("set", set);
        return "product/productdetail"; // 상품 상세 정보를 보여줄 뷰의 이름
    }

}
