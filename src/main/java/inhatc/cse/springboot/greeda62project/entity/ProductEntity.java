package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class ProductEntity {

    @Id
    private int number;
    private String productName;
    private String productSize;
    private String productPrice;
    private String productDescription;
}
