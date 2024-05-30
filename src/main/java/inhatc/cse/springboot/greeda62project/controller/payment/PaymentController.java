package inhatc.cse.springboot.greeda62project.controller.payment;

import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.entity.PaymentEntity;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/complete")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            paymentService.savePayment(paymentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "결제가 정상적으로 처리되었습니다"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "결제에 실패하였습니다. 실패 사유: " + e.getMessage()));
        }
    }
}
