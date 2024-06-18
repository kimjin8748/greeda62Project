package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/*모든 상품 정보 Entity*/
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//Single_Table 전략 사용
@DiscriminatorColumn(name = "product_type")//product_type 별로 테이블을 분류
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class ProductEntity {

    @Id
    private String serialNumber;

    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    @OneToMany(mappedBy = "product")
    private List<OrderItemEntity> orderItems;

    public ProductEntity(String serialNumber, String productName, String productSize, int productPrice, String productDescription) {
        this.serialNumber = serialNumber;
        this.productName = productName;
        this.productSize = productSize;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
    }
}
