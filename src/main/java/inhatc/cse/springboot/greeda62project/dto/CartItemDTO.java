package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDTO { //장바구니 상품 로직 처리 위한 DTO

    private ProductDTO product;
    private int amount;
}
