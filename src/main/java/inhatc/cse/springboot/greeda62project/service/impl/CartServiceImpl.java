package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.CartItemDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.repository.CartItemRepository;
import inhatc.cse.springboot.greeda62project.repository.CartRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductRepository;
import inhatc.cse.springboot.greeda62project.service.CartService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final HttpSession session;

    @Override
    public void addCart(MemberDTO memberDTO, ProductDTO productDTO, int amount) {
        // 상품 ID를 사용하여 상품 엔티티를 찾습니다.
        ProductEntity productEntity = productRepository.findById(productDTO.getSerialNumber())
                .orElseThrow(() -> new IllegalArgumentException("상품 ID [" + productDTO.getSerialNumber() + "]에 해당하는 상품을 찾을 수 없습니다."));

        // 회원 ID를 사용하여 카트 엔티티를 찾습니다. (CartRepository에 적절한 메서드가 구현되어 있다고 가정)
        CartEntity cartEntity = cartRepository.findByMemberEntity_Id(memberDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원 ID [" + memberDTO.getId() + "]에 해당하는 카트를 찾을 수 없습니다."));

        // 제품 ID와 카트 ID를 사용하여 CartItemEntity를 찾습니다.
        Optional<CartItemEntity> optionalCartItemEntity = cartItemRepository.findByCartEntityAndProductEntity(cartEntity, productEntity);

        CartItemEntity cartItemEntity;
        if (optionalCartItemEntity.isPresent()) {
            // 상품이 장바구니에 이미 존재한다면 수량만 증가
            cartItemEntity = optionalCartItemEntity.get();
            cartItemEntity.addCount(amount);
        } else {
            // 그렇지 않다면 새로운 CartItemEntity 생성
            cartItemEntity = CartItemEntity.createCartItem(cartEntity, productEntity, amount);
        }

        // 장바구니 아이템을 저장합니다.
        cartItemRepository.save(cartItemEntity);
    }

    @Override
    public List<CartItemEntity> getCartItems(int cartId) {
        return cartItemRepository.findByCartEntityId(cartId);
    }


}
