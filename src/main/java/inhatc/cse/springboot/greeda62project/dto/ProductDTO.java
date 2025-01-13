package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

/*모든 상품 처리 위한 DTO*/
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
    private MultipartFile productPhoto; // 파일 업로드 처리 위한 필드
    private String photoFileName;
    private String imageUrl;

    public ProductDTO(String serialNumber, String productName, String productSize, int productPrice, String productDescription, String imageUrl, String photoFileName) {
        this.serialNumber = serialNumber;
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.imageUrl = imageUrl;
        this.photoFileName = photoFileName;
    }

    public static ProductDTO toProductDTO(ProductEntity productEntity) {
        ProductDTO dto = new ProductDTO();
        dto.setSerialNumber(productEntity.getSerialNumber());
        dto.setProductName(productEntity.getProductName());
        dto.setProductSize(productEntity.getProductSize());
        dto.setProductPrice(productEntity.getProductPrice());
        dto.setProductDescription(productEntity.getProductDescription());
        dto.setImageUrl(productEntity.getImageUrl());
        dto.setPhotoFileName(productEntity.getPhotoFileName());
        return dto;
    }

}
