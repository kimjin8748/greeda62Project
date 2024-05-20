package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CartItemDTO {

    private ProductDTO product;
    private int amount;
}
