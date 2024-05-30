package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaymentDTO {
    private Long id;
    private String paymentId;
    private int amount;
    private LocalDateTime paymentDate;
    private List<ProductDTO> products; // ProductDTO는 ProductEntity의 DTO 클래스입니다.
}
