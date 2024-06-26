package inhatc.cse.springboot.greeda62project.controller.payment;

import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/*결제API을 위한 컨트롤러*/
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /*결제 진행시 API 로직*/
    @PostMapping("/complete")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            paymentService.savePayment(paymentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "결제가 정상적으로 처리되었습니다"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "결제에 실패하였습니다. 실패 사유: " + e.getMessage()));
        }
    }

    //결제 취소시 API 로직(구현x)
    @PostMapping("/cancel")
    public ResponseEntity<Map<String, String>> cancelPayment(@RequestBody Map<String, Object> cancelRequest) {
        String impUid = (String) cancelRequest.get("impUid");
        String reason = (String) cancelRequest.get("reason");
        Integer cancelRequestAmount = (Integer) cancelRequest.get("cancel_request_amount");
        System.out.println(impUid + ", " + reason + ", " + cancelRequestAmount);

        Map<String, String> response = new HashMap<>();

        try {
            boolean isCancelled = paymentService.cancelPayment(impUid, reason, cancelRequestAmount);

            if (isCancelled) {
                response.put("message", "결제가 취소되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("message", "결제 취소에 실패하였습니다.");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "결제 취소 처리 중 문제가 발생했습니다.");
            System.err.println("결제 취소 처리 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(response);
        }
    }
}

