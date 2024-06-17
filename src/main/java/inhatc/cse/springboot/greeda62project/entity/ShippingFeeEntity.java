package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingFeeEntity { //멤버쉽 별 배송비 정보 Entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "membership_level_id")
    private MembershipLevelEntity membershipLevelEntity;  // 멤버십 등급 참조

    private int fee;  // 배송비
}
