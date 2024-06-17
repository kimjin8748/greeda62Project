package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SucculentDTO { //다육이 상품 처리 위한 DTO

    private String serialNumber;
    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    public static SucculentDTO toSucculentDTO(SucculentEntity succulentEntity) {
        SucculentDTO dto = new SucculentDTO();
        dto.setSerialNumber(succulentEntity.getSerialNumber());
        dto.setProductName(succulentEntity.getProductName());
        dto.setProductSize(succulentEntity.getProductSize());
        dto.setProductPrice(succulentEntity.getProductPrice());
        dto.setProductDescription(succulentEntity.getProductDescription());
        return dto;
    }
}
