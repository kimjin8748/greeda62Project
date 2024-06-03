package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderInfoDTO {

    private String paymentId;
    private String serialNumber;
    private String productName;
    private int quantity;
    private int amount;
    private LocalDateTime paymentDate;
}
