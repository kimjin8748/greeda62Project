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
    private String Number;

    @OneToOne
    @JoinColumn(name = "pot_id")
    private PotEntity potEntity;

    @OneToOne
    @JoinColumn(name = "succulent_id")
    private SucculentEntity succulentEntity;

    @OneToOne
    @JoinColumn(name = "set_id")
    private SetEntity setEntity;
}
