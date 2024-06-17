package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PotDTO { //화분 상품 처리하는 DTO

    private String serialNumber;
    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    public static PotDTO toPotDTO(PotEntity potEntity) {
        PotDTO dto = new PotDTO();
        dto.setSerialNumber(potEntity.getSerialNumber());
        dto.setProductName(potEntity.getProductName());
        dto.setProductSize(potEntity.getProductSize());
        dto.setProductPrice(potEntity.getProductPrice());
        dto.setProductDescription(potEntity.getProductDescription());
        return dto;
    }
}
