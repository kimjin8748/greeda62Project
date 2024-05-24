package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String main(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                       @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                       Model model, HttpSession session) {

        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin != null && !isAdmin) {
            model.addAttribute("adminAccessDenied", true);
            session.removeAttribute("isAdmin"); // 플래시 속성 제거
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> page = productService.findAllProducts(pageable);
        List<ProductDTO> products = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);

        return "main";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(required = false) String keyword, Model model) {
        List<ProductEntity> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByKeyword(keyword);
        } else {
            // keyword가 없는 경우의 처리 로직
            products = new ArrayList<>();
        }

        // ProductEntity 리스트를 ProductDTO 리스트로 변환
        List<ProductDTO> productDTOSs = products.stream().map(ProductDTO::toProductDTO).collect(Collectors.toList());

        model.addAttribute("products", productDTOSs);
        model.addAttribute("isEmpty", products.isEmpty());
        model.addAttribute("keyword", keyword);

        return "search/searchResult"; // 검색 결과를 보여줄 페이지의 이름
    }
}
