package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DiscriminatorValue("SET")
@Table(name = "product")
public class SetEntity extends ProductEntity {
    public SetEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription) {
        super(serialNumber, productName, productSize, productPrice, productDescription);
    }
    // SetEntity에 특화된 필드가 있다면 여기에 추가
}