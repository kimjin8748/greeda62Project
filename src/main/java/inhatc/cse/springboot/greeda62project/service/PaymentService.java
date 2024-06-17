package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.OrderInfoDTO;
import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

/*결제 정보 처리 Service 메소드 선언*/
public interface PaymentService {

    /*결제 정보 DB에 저장하는 Service 로직*/
    void savePayment(PaymentDTO paymentDTO);

    /*회원별 주문 정보 DB에서 가져오는 Service 로직*/
    List<OrderInfoDTO> getOrdersByCurrentLoggedInUser(HttpSession session);

    /*결제 취소 처리 Service 로직(구현x)*/
    boolean cancelPayment(String impUid, String reason, Integer cancelRequestAmount);

}
