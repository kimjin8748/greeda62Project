package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String main(Model model){
        List<ProductDTO> products = productService.findAllProducts();
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
        List<ProductDTO> productDTOs = products.stream().map(ConvertToProductDTO::convertToDTO).collect(Collectors.toList());

        model.addAttribute("products", productDTOs);
        return "search/searchResult"; // 검색 결과를 보여줄 페이지의 이름
    }
}
