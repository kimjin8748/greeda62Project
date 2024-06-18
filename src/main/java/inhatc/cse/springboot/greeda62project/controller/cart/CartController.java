package inhatc.cse.springboot.greeda62project.controller.cart;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;
import inhatc.cse.springboot.greeda62project.service.CartService;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.service.MembershipService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    private final MembershipService membershipService;

    /*장바구니 이동 로직*/
    @GetMapping("/cart")
    public String showCart(HttpSession session, Model model) {
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }

        // 현재 사용자의 멤버쉽 정보 가져오기
        MembershipLevelEntity currentUserMembershipLevel = null;
        ShippingFeeEntity currentUserShippingFee = null;

        try {
            currentUserMembershipLevel = membershipService.getUserMembershipLevel(id);
            if (currentUserMembershipLevel != null) {
                currentUserShippingFee = membershipService.getShippingFeeByMembershipLevel(currentUserMembershipLevel.getId());
            }
        } catch (IllegalStateException e) {
            // 예외 처리: 사용자에게 멤버십 레벨이 없는 경우
            model.addAttribute("error", "현재 사용자는 멤버십 레벨이 없습니다.");
        }

        MemberDTO memberDTO = memberService.findMemberById(id);
        List<CartItemEntity> cartItems = cartService.getCartItemsByUserId(id);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("member", memberDTO);
        model.addAttribute("currentUserMembershipLevel", currentUserMembershipLevel);
        model.addAttribute("currentUserShippingFee", currentUserShippingFee);
        return "cart/cart";
    }

    /*장바구니에 상품 추가 로직*/
    @PostMapping("/cart/{id}/{productId}")
    public String addCartItem(@PathVariable("id") String id, @PathVariable("productId") String productId, int amount, HttpSession session) {
        String memberId = (String) session.getAttribute("id");
        if (memberId == null) {
            return "redirect:/member";
        }
        MemberDTO memberDTO = memberService.findMemberById(id);
        ProductDTO productDTO = productService.productView(productId);

        try {
            cartService.addCart(memberDTO, productDTO, amount);
        } catch (IllegalStateException e) {
            // 이미 구매된 상품일 경우 적절한 처리 (예: 에러 메시지 표시)
            return "redirect:/cart?error=alreadyPurchased";
        }

        return "redirect:/cart";
    }

    /*장바구니에서 상품 삭제 로직*/
    @DeleteMapping("/cart/{id}/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable("id") String id, @PathVariable("productId") String productId) {
        System.out.println("Controller: removeCartItem called with id=" + id + " and productId=" + productId);
        boolean isRemoved = cartService.removeCartItem(id, productId);

        if(isRemoved) {
            // 성공적으로 항목이 삭제되었을 경우, 200 OK 응답을 반환
            return ResponseEntity.ok().body("Product with ID " + productId + " removed from cart");
        } else {
            // 항목 삭제에 실패했을 경우, 예를 들어 항목이 존재하지 않는 경우, 404 Not Found 또는 적절한 상태 코드를 반환
            return ResponseEntity.notFound().build();
        }
    }

}
