package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

import java.time.LocalDateTime;

/*주문정보 처리하는 DTO*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderInfoDTO {

    private String paymentId;
    private String impUid;
    private String serialNumber;
    private String productName;
    private int quantity;
    private int amount;
    private LocalDateTime paymentDate;
}
