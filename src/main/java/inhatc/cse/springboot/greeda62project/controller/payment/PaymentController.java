package inhatc.cse.springboot.greeda62project.controller.payment;

import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.entity.PaymentEntity;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/complete")
    public ResponseEntity<PaymentEntity> completePayment(@RequestBody PaymentEntity paymentEntity) {
        PaymentEntity savedPayment = paymentService.savePayment(paymentEntity);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }
}
