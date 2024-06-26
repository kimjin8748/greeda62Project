package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*결제 처리 위한 DTO*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentDTO {
    private String paymentId;
    private String impUid;
    private int amount;
    private LocalDateTime paymentDate;
    private List<ProductDTO> products; // ProductDTO는 ProductEntity의 DTO 클래스입니다.
    private String memberId;
}
