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
@DiscriminatorValue("POT")//Single_Table 전략으로 'POT'으로 나눠진 상품이 PotEntity
@Table(name = "product")
public class PotEntity extends ProductEntity { //화분 상품 정보 Entity
    public PotEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription) {
        super(serialNumber, productName, productSize, productPrice, productDescription);
    }
    // PotEntity에 특화된 필드가 있다면 추가
}
