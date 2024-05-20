package inhatc.cse.springboot.greeda62project.controller.cart;

import inhatc.cse.springboot.greeda62project.dto.CartItemDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.CartService;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final MemberService memberService;
    private final ProductService productService;
    private final CartService cartService;

    //장바구니 이동 컨트롤러
    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        List<CartItemDTO> cartItems = cartService.getCartItems(id);
        model.addAttribute("cartItems", cartItems);
        return "cart/cart";
    }

    // 장바구니에 물건 넣기 컨트롤러
//    @PostMapping("/cart/{id}/{productId}")
//    public String addCartItem(@PathVariable("id") String id, @PathVariable("productId") String productId, int amount) {
//
//        MemberDTO memberDTO = memberService.findUser(id);
//        ProductDTO productDTO = productService.productView(productId);
//
//        cartService.addCart(memberDTO, productDTO, amount);
//
//        return "redirect:cart/cart";
//    }
}
