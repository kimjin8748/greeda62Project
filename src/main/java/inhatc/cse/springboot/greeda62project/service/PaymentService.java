package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.OrderInfoDTO;
import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.OrderItemEntity;
import inhatc.cse.springboot.greeda62project.entity.PaymentEntity;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface PaymentService {

    void savePayment(PaymentDTO paymentDTO);

    List<OrderInfoDTO> getOrdersByCurrentLoggedInUser(HttpSession session);

    boolean cancelPayment(String impUid, String reason);

}
