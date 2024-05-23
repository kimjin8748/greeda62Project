package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "product_type")
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
}
