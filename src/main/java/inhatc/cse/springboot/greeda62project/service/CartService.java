package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;

import java.util.List;

/*장바구니 상품 정보 처리 Service 메소드 선언*/
public interface CartService {

    /*장바구니에 상품 추가하는 Service 로직*/
    void addCart(MemberDTO memberDTO, ProductDTO productDTO, int amount);

    /*장바구니에 담겨 있는 상품 DB에서 불러오는 Service 로직*/
    List<CartItemEntity> getCartItemsByUserId(String memberId);

    /*결제 완료시 장바구니에서 상품 삭제하는 Service 로직*/
    boolean removeCartItem(String memberId, String productId);

}
