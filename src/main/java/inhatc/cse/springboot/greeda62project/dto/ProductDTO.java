package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ProductDTO { //모든 상품 처리 위한 DTO


    private String serialNumber;
    private String productType;
    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;
    private MultipartFile productPhoto; // 파일 업로드 처리 위한 필드
    private String photoFileName;

    public ProductDTO(String serialNumber, String productName, String productSize, int productPrice, String productDescription) {
        this.serialNumber = serialNumber;
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }

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
