package inhatc.cse.springboot.greeda62project.controller.mypage;

import inhatc.cse.springboot.greeda62project.dto.OrderInfoDTO;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final PaymentService paymentService;

    @GetMapping("/delivery")
    public String delivery(Model model, HttpSession session) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        List<OrderInfoDTO> orders = paymentService.getOrdersByCurrentLoggedInUser(session);
        model.addAttribute("orders", orders);
        return "mypage/delivery";
    }
}
