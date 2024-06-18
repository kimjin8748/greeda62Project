package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*결제 정보 Entity*/
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String paymentId; // 결제 고유 ID
    private int amount; // 결제 금액
    private String impUid;
    private LocalDateTime paymentDate; // 결제 일시

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderEntity> orders;

    @PrePersist
    public void onPrePersist() {
        this.paymentDate = LocalDateTime.now();
    }
}
