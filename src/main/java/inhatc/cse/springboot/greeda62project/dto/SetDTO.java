package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SetDTO {

    private String serialNumber;
    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    public static SetDTO toSetDTO(SetEntity setEntity) {
        SetDTO dto = new SetDTO();
        dto.setSerialNumber(setEntity.getSerialNumber());
        dto.setProductName(setEntity.getProductName());
        dto.setProductSize(setEntity.getProductSize());
        dto.setProductPrice(setEntity.getProductPrice());
        dto.setProductDescription(setEntity.getProductDescription());
        return dto;
    }
}
