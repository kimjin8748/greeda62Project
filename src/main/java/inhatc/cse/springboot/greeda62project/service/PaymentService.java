package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.PaymentEntity;

import java.util.List;

public interface PaymentService {

    void savePayment(PaymentDTO paymentDTO);
}
