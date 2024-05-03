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
@Table(name = "sets")
public class SetEntity {

    @Id
    private String serialNumber;

    private String productName;
    private String productSize;
    private int productPrice;
    private String productDescription;

    @OneToMany(mappedBy = "setEntity")
    private List<ProductEntity> products = new ArrayList<>();
}
