package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_item")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private CartEntity cartEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private ProductEntity productEntity;

    private int count; // 상품 개수

    public static CartItemEntity createCartItem(CartEntity cartEntity, ProductEntity productEntity, int amount) {
        return CartItemEntity.builder()
                .cartEntity(cartEntity)
                .productEntity(productEntity)
                .count(amount)
                .build();
    }

    // 이미 담겨있는 물건 또 담을 경우 수량 증가
    public void addCount(int count) {
        this.count += count;
    }
}
