package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShippingFeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "membership_level_id")
    private MembershipLevelEntity membershipLevelEntity;  // 멤버십 등급 참조

    private int fee;  // 배송비
}
