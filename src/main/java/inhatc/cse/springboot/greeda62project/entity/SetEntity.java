package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/*Set 상품 정보 Entity*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("SET")//Single_Table 전략으로 'SET'으로 나눠진 상품이 SetEntity
@Table(name = "product")
public class SetEntity extends ProductEntity {
    public SetEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription, String imageUrl, String photoFileName) {
        super(serialNumber, productName, productSize, productPrice, productDescription, imageUrl, photoFileName);
    }
}