package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("SET")//Single_Table 전략으로 'SET'으로 나눠진 상품이 SetEntity
@Table(name = "product")
public class SetEntity extends ProductEntity { //Set 상품 정보 Entity
    public SetEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription) {
        super(serialNumber, productName, productSize, productPrice, productDescription);
    }
    // SetEntity에 특화된 필드가 있다면 여기에 추가
}