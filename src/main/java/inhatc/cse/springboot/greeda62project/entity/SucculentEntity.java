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
@AllArgsConstructor
@Builder
@Table(name = "succulent")
public class SucculentEntity {

    @Id
    private String serialNumber;

    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    @OneToOne(mappedBy = "succulentEntity")
    private ProductEntity products;
}
