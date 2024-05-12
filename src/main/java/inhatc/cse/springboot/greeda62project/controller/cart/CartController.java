package inhatc.cse.springboot.greeda62project.controller.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class CartController {

    @GetMapping("/cart")
    public String showCart() {
        return "cart/cart";
    }
}
