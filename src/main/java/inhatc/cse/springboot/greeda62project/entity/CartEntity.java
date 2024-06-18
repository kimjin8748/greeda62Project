package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*회원별 장바구니 정보 Entity*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private MemberEntity memberEntity; // 구매자

    private int count; // 카트에 담긴 총 상품 개수

    @OneToMany(mappedBy = "cartEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItemEntity> cartItems = new ArrayList<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createDate; // 날짜

    @PrePersist
    public void onCreate(){
        this.createDate = LocalDate.now();
    }

    public static CartEntity createCart(MemberEntity memberEntity) {
        CartEntity cart = new CartEntity();
        cart.setCount(0);
        cart.setMemberEntity(memberEntity);
        return cart;
    }

    public void addCartItem(CartItemEntity cartItem) {
        this.cartItems.add(cartItem);
        cartItem.setCartEntity(this);
    }

    public void removeCartItem(CartItemEntity cartItem) {
        this.cartItems.remove(cartItem);
        cartItem.setCartEntity(null);
    }
}
