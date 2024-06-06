package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MembershipLevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String levelName;  // 등급 이름 (예: 새싹, 씨앗, 꽃)
    private String description;  // 등급 설명

}
