package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDTO {


    private int number;
    private String productName;
    private String productSize;
    private String productPrice;
    private String productDescription;

    public static ProductDTO toMemberDTO(ProductEntity productEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setNumber(productEntity.getNumber());
        dto.setProductName(productEntity.getProductName());
        dto.setProductSize(productEntity.getProductSize());
        dto.setProductPrice(productEntity.getProductPrice());
        dto.setProductDescription(productEntity.getProductDescription());
        return dto;
    }

}
