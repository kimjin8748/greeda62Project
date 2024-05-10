package inhatc.cse.springboot.greeda62project.controller.category;

import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
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
public class SucculentController {

    private final ProductService productService;

    @GetMapping("/succulent")
    public String succulent(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                            @RequestParam(value = "pageSize", defaultValue = "4") int pageSize,
                            Model model) {
        Page<SucculentEntity> page = productService.findSucculentPaginated(pageNo, pageSize);
        List<SucculentEntity> succulentList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("succulentList", succulentList);

        return "/category/succulent";
    }
}
