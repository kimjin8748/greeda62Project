package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SetController {

    private final ProductService productService;
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
}
