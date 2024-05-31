package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.*;
import inhatc.cse.springboot.greeda62project.repository.*;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        // 결제 정보를 사용하여 PaymentEntity 생성 및 저장
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentId(paymentDTO.getPaymentId())
                .amount(paymentDTO.getAmount())
                // paymentDate는 @PrePersist를 통해 자동으로 현재 시간으로 설정됩니다.
                .build();
        paymentRepository.save(paymentEntity);

        String memberId = paymentDTO.getMemberId(); // PaymentDTO에 memberId를 추가했다고 가정합니다.
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid memberId: " + memberId));

        // OrderEntity 생성 및 저장
        OrderEntity orderEntity = OrderEntity.builder()
                .member(member)
                .payment(paymentEntity)
                .build();
        OrderEntity savedOrder = orderRepository.save(orderEntity);

        // 결제된 상품 목록을 가져와 OrderItemEntity 생성 및 저장
        List<ProductDTO> products = paymentDTO.getProducts(); // 결제된 상품 목록을 가져옴
        System.out.println(products);
        for (ProductDTO product : products) {
            if (product.getSerialNumber() == null) {
                throw new IllegalArgumentException("Product serial number must not be null");
            }

            OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                    .order(savedOrder)
                    .product(productRepository.findById(product.getSerialNumber())
                            .orElseThrow(() -> new EntityNotFoundException("Product with serial number " + product.getSerialNumber() + " not found")))
                    .quantity(1) // 필요에 따라 수량 설정
                    .build();
            orderItemRepository.save(orderItemEntity);
        }
    }
}
