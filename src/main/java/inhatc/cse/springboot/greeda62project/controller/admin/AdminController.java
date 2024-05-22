package inhatc.cse.springboot.greeda62project.controller.admin;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final MemberService memberService;

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String id = (String) session.getAttribute("id");

        if (id == null) {
            redirectAttributes.addFlashAttribute("isAdmin", false);
            return "redirect:/"; // 세션에 id가 없으면 홈으로 리디렉션, "isAdmin" 플래시 속성을 false로 설정
        }

        MemberDTO memberDTO = memberService.findUser(id);

        if (memberDTO == null || !memberDTO.getId().equals("admin")) {
            redirectAttributes.addFlashAttribute("isAdmin", false);
            return "redirect:/"; // 사용자가 null이거나 관리자가 아니면 홈으로 리디렉션, "isAdmin" 플래시 속성을 false로 설정
        }

        model.addAttribute("isAdmin", true); // 관리자 여부를 모델에 추가
        return "admin/admin"; // 관리자 페이지로 이동
    }

    @GetMapping("/admin/memberCTR")
    public String memberControl(Model model){
        List<MemberDTO> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "admin/memberCTR";
    }

    @GetMapping("/admin/productcheck")
    public String listProducts(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                               @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                               Model model) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> page = productService.findAllProducts(pageable);
        List<ProductDTO> products = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);

        return "admin/product_check"; // 상품 목록을 보여주는 View 이름
    }
    @GetMapping("/admin/productinsert")
    public String productinsert(){
        return "admin/product_insert";
    }

    @GetMapping("/admin/productupdate")
    public String productupdate(){
        return "admin/product_update";
    }

}
