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
@Builder
@DiscriminatorValue("SUC")
@Table(name = "product")
public class SucculentEntity extends ProductEntity {
    // SucculentEntity에 특화된 필드가 있다면 추가
}
