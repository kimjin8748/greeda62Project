package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /*모든상품 페이지 이동 로직*/
    @GetMapping("/allproduct")
    public String allProductForm(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                 @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                                 Model model) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> page = productService.findAllProducts(null, pageable);
        List<ProductDTO> products = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);

        return "/category/allproduct";
    }

    /*화분 페이지 이동 로직*/
    @GetMapping("/pot")
    public String potForm(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                          @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                          Model model) {
        // PotEntity 클래스를 기반으로 특정 카테고리의 상품을 페이지네이션하여 조회
        Page<ProductEntity> page = productService.findProductByCategoryPaginated(PotEntity.class, pageNo, pageSize);
        List<ProductEntity> potList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("potList", potList);

        return "/category/pot";
    }

    /*다육이&화분Set 페이지 이동 로직*/
    @GetMapping("/set")
    public String setForm(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                          @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                          Model model) {
        // PotEntity 클래스를 기반으로 특정 카테고리의 상품을 페이지네이션하여 조회
        Page<ProductEntity> page = productService.findProductByCategoryPaginated(SetEntity.class, pageNo, pageSize);
        List<ProductEntity> setList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("setList", setList);

        return "/category/set";
    }

    /*다육이 페이지 이동 로직*/
    @GetMapping("/succulent")
    public String succulentForm(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "8") int pageSize,
                                Model model) {
        // PotEntity 클래스를 기반으로 특정 카테고리의 상품을 페이지네이션하여 조회
        Page<ProductEntity> page = productService.findProductByCategoryPaginated(SucculentEntity.class, pageNo, pageSize);
        List<ProductEntity> succulentList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("succulentList", succulentList);

        return "/category/succulent";
    }
}
