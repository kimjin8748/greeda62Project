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
@DiscriminatorValue("SUC")
@Table(name = "product")
public class SucculentEntity extends ProductEntity {
    public SucculentEntity(String serialNumber, String productType, String productName, String productSize, int productPrice, String productDescription) {
        super(serialNumber, productName, productSize, productPrice, productDescription);
    }
    // SucculentEntity에 특화된 필드가 있다면 추가
}
