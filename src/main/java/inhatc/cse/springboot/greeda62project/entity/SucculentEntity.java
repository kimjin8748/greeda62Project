package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("SUC")//Single_Table 전략으로 'SUC'으로 나눠진 상품이 SucculentEntity
@Table(name = "product")
public class SucculentEntity extends ProductEntity {//다육이 상품 정보 Entity
    public SucculentEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription) {
        super(serialNumber, productName, productSize, productPrice, productDescription);
    }
    // SucculentEntity에 특화된 필드가 있다면 추가
}
