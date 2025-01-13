package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

/*다육이 상품 정보 Entity*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("SUC")//Single_Table 전략으로 'SUC'으로 나눠진 상품이 SucculentEntity
@Table(name = "product")
public class SucculentEntity extends ProductEntity {
    public SucculentEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription, String imageUrl, String photoFileName) {
        super(serialNumber, productName, productSize, productPrice, productDescription, imageUrl, photoFileName);
    }
}
