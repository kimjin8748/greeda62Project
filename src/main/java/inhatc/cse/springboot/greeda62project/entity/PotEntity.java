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
@DiscriminatorValue("POT")
@Table(name = "product")
public class PotEntity extends ProductEntity {
    // PotEntity에 특화된 필드가 있다면 추가
}
