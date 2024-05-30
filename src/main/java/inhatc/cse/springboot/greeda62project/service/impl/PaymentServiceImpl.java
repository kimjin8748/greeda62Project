package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.*;
import inhatc.cse.springboot.greeda62project.repository.*;
import inhatc.cse.springboot.greeda62project.service.CartService;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        // 결제 정보를 사용하여 PaymentEntity 생성 및 저장
        PaymentEntity paymentEntity = new PaymentEntity();
        // PaymentEntity에 필요한 정보 설정
        paymentRepository.save(paymentEntity);

        // OrderEntity 생성 및 저장
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setPayment(paymentEntity);
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // 결제된 상품 목록을 가져와 OrderItemEntity 생성 및 저장
        List<ProductDTO> products = paymentDTO.getProducts(); // 결제된 상품 목록을 가져옴
        for (ProductDTO product : products) {
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrder(savedOrder);
            orderItemEntity.setProduct(productRepository.findById(product.getSerialNumber()).orElseThrow(EntityNotFoundException::new));
            orderItemEntity.setQuantity(1); // 필요에 따라 수량 설정
            orderItemRepository.save(orderItemEntity);
        }
    }


}
