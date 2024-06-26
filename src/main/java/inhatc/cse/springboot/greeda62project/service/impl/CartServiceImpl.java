package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.repository.*;
import inhatc.cse.springboot.greeda62project.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*장바구니 상품 정보 처리 Service 메소드 구현*/
@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderItemRepository orderItemRepository;

    /*장바구니에 상품 추가하는 Service 로직*/
    @Override
    public void addCart(MemberDTO memberDTO, ProductDTO productDTO, int amount) {
        // 상품 ID를 사용하여 상품 엔티티를 찾습니다.
        ProductEntity productEntity = productRepository.findById(productDTO.getSerialNumber())
                .orElseThrow(() -> new IllegalArgumentException("상품 ID [" + productDTO.getSerialNumber() + "]에 해당하는 상품을 찾을 수 없습니다."));

        // 해당 상품이 다른 사용자에게 이미 구매되었는지 확인합니다.
        if (orderItemRepository.existsByProduct(productEntity)) {
            throw new IllegalStateException("해당 상품은 이미 다른 사용자에 의해 구매되었습니다.");
        }

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

        // 카트의 총 상품 개수를 업데이트합니다.
        int totalItemCount = cartEntity.getCartItems().stream()
                .mapToInt(CartItemEntity::getCount)
                .sum();
        cartEntity.setCount(totalItemCount);

        // 카트를 저장합니다.
        cartRepository.save(cartEntity);
    }

    /*장바구니에 담겨 있는 상품 DB에서 불러오는 Service 로직*/
    @Override
    public List<CartItemEntity> getCartItemsByUserId(String memberId) {
        Optional<CartEntity> cartEntityOptional = cartRepository.findByMemberEntity_Id(memberId);
        if (cartEntityOptional.isPresent()) {
            int cartId = cartEntityOptional.get().getId();
            return cartItemRepository.findByCartEntityId(cartId);
        } else {
            // 장바구니가 없을 경우 빈 리스트 반환
            return List.of();
        }
    }

    /*결제 완료시 장바구니에서 상품 삭제하는 Service 로직*/
    @Transactional
    @Override
    public boolean removeCartItem(String memberId, String productId) {
        System.out.println("Service: removeCartItem called with memberId=" + memberId + " and productId=" + productId);
        Optional<CartEntity> cartOptional = cartRepository.findByMemberEntity_Id(memberId);
        if (cartOptional.isPresent()) {
            CartEntity cart = cartOptional.get();

            // 여기서는 ID가 String이라고 가정하고 있습니다. 실제로는 Long 또는 Integer일 수 있습니다.
            // ProductEntity를 찾는 부분을 cart가 존재할 때만 수행합니다.
            Optional<ProductEntity> productOptional = productRepository.findById(productId);
            if (productOptional.isPresent()) {
                ProductEntity productEntity = productOptional.get();

                // 장바구니 항목에서 해당 상품을 삭제합니다.
                boolean removed = cart.getCartItems().removeIf(item ->
                        item.getProductEntity().getSerialNumber().equals(productId));

                // 실제로 항목이 삭제되었다면 변경된 상태를 저장합니다.
                if (removed) {
                    System.out.println("Service: Product removed from cart");
                    cartRepository.save(cart);
                    return true;
                } else {
                    System.out.println("Service: Product not found in cart");
                }
            } else {
                System.out.println("Service: Product not found with productId=" + productId);
            }
        } else {
            System.out.println("Service: Cart not found with memberId=" + memberId);
        }
        return false;
    }



}
