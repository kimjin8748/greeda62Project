package inhatc.cse.springboot.greeda62project.controller.product;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
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

    //상품 상세 페이지 이동 로직
    @GetMapping("/productdetail")
    public String showProductDetail(@RequestParam("id") String serialNumber, Model model, HttpSession session) {
        ProductDTO productDTO = productService.findById(serialNumber);
            // productDTO가 null인 경우 로그 출력
        model.addAttribute("products", productDTO);
        System.out.println(productDTO);

        String productId = productDTO.getSerialNumber();
        boolean isPurchased = productService.isProductPurchased(productId);

        if(isPurchased) {
            model.addAttribute("purchaseMessage", "다른 사용자가 이미 구매한 상품입니다.");
        }
        return "product/productdetail"; // 상품 상세 정보를 보여줄 뷰의 이름
    }


}
