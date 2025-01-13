package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

/*화분 상품 정보 Entity*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("POT")//Single_Table 전략으로 'POT'으로 나눠진 상품이 PotEntity
@Table(name = "product")
public class PotEntity extends ProductEntity {
    public PotEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription, String imageUrl, String photoFileName) {
        super(serialNumber, productName, productSize, productPrice, productDescription, imageUrl, photoFileName);
    }
}
