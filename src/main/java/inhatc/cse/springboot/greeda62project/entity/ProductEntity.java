package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
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
    private String serialNumber;

    private String productName;
    private String productSize;
    private String productPrice;
    private String productDescription;

    @ManyToOne
    @JoinColumn(name = "pot_id")
    private PotEntity potEntity;

    @ManyToOne
    @JoinColumn(name = "succulent_id")
    private SucculentEntity succulentEntity;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private SetEntity setEntity;
}
