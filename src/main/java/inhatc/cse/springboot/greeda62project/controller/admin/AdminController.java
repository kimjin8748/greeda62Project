package inhatc.cse.springboot.greeda62project.controller.admin;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminController {

    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/")
    public String admin(){
        return "/admin/admin";
    }

    @GetMapping("/memberCTR")
    public String memberControl(Model model){
        List<MemberDTO> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "/admin/memberCTR";
    }

//    @GetMapping("/productcheck")
//    public String listProducts(Model model) {
//        List<ProductDTO> products = productService.findAllProducts();
//        model.addAttribute("products", products);
//        return "admin/product_check"; // 상품 목록을 보여주는 View 이름
//    }
}
