package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

/*장바구니 상품 로직 처리 위한 DTO*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDTO {

    private ProductDTO product;
    private int amount;
}
