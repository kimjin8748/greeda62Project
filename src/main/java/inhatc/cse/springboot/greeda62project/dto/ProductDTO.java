package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDTO {


    private String serialNumber;
    private String productType;
    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    public static ProductDTO toProductDTO(ProductEntity productEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setSerialNumber(productEntity.getSerialNumber());
        dto.setProductName(productEntity.getProductName());
        dto.setProductSize(productEntity.getProductSize());
        dto.setProductPrice(productEntity.getProductPrice());
        dto.setProductDescription(productEntity.getProductDescription());
        return dto;
    }

}
