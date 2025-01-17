package inhatc.cse.springboot.greeda62project.controller.mypage;

import inhatc.cse.springboot.greeda62project.dto.OrderInfoDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReturnChangeCenController {

    private final PaymentService paymentService;

    /*반품/교환/취소 페이지 이동 로직*/
    @GetMapping("/return_change_cen")
    public String returnChange(HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        return "mypage/return_change_cen";
    }

    @GetMapping("/cancel")
    public String cancel(HttpSession session, Model model) {
        List<OrderInfoDTO> orders = paymentService.getOrdersByCurrentLoggedInUser(session);
        model.addAttribute("orders", orders);
        return "mypage/cancel";
    }
}

