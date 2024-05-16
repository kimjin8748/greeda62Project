package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.repository.CartItemRepository;
import inhatc.cse.springboot.greeda62project.repository.CartRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductRepository;
import inhatc.cse.springboot.greeda62project.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void addCart(MemberDTO memberDTO, ProductDTO productDTO, int amount) {
        // 상품 ID와 회원 ID를 사용하여 상품 엔티티와 카트 엔티티를 찾습니다.
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(productDTO.getSerialNumber());
        Optional<CartEntity> optionalCartEntity = cartRepository.findById(memberDTO.getId());

        // Optional 객체에서 실제 값을 가져오는 과정에서 값이 존재하지 않으면 예외를 발생시킵니다.
        ProductEntity productEntity = optionalProductEntity.orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
        CartEntity cartEntity = optionalCartEntity.orElseThrow(() -> new IllegalArgumentException("해당 카트를 찾을 수 없습니다."));

        // 제품 ID와 카트 ID를 사용하여 CartItemEntity를 찾습니다.
        CartItemEntity cartItemEntity = cartItemRepository.findByCartIdAndProductNumber(cartEntity.getId(), productEntity.getNumber()).orElse(null);

        // 상품이 장바구니에 존재하지 않는다면 카트상품 생성 후 추가
        if (cartItemEntity == null) {
            cartItemEntity = CartItemEntity.createCartItem(cartEntity, productEntity, amount);
        }
        // 상품이 장바구니에 이미 존재한다면 수량만 증가
        else {
            cartItemEntity.addCount(amount);
        }

        // 카트 상품 총 개수 증가 로직 (이 부분은 Cart 엔티티의 구현에 따라 달라질 수 있습니다)
        // 만약 Cart 엔티티에 총 수량을 관리하는 로직이 있다면 여기서 업데이트를 해주어야 합니다.
        // 예제 코드에서는 해당 부분의 상세 구현을 생략합니다.

        // 장바구니 아이템과 장바구니를 저장합니다.
        cartItemRepository.save(cartItemEntity);
        cartRepository.save(cartEntity);
    }

}
