package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.OrderInfoDTO;
import inhatc.cse.springboot.greeda62project.dto.PaymentDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.dto.TokenResponse;
import inhatc.cse.springboot.greeda62project.entity.*;
import inhatc.cse.springboot.greeda62project.repository.*;
import inhatc.cse.springboot.greeda62project.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;

    @Value("${portone.api.key}")
    private String apiKey;

    @Value("${portone.api.secret}")
    private String apiSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getAccessToken() {
        // PortOne API를 통해 액세스 토큰 발급
        String url = "https://api.iamport.kr/users/getToken";

        System.out.println(apiKey + " ," + apiSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> body = new HashMap<>();
        body.put("imp_key", apiKey);
        body.put("imp_secret", apiSecret);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(url, entity, TokenResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            TokenResponse tokenResponse = response.getBody();
            return tokenResponse.getResponse().getAccess_token();
        } else {
            throw new RuntimeException("Failed to get access token from PortOne");
        }
    }

    @Override
    public void savePayment(PaymentDTO paymentDTO) {
        // 결제 정보를 사용하여 PaymentEntity 생성 및 저장
        PaymentEntity paymentEntity = PaymentEntity.builder()
                .paymentId(paymentDTO.getPaymentId())
                .amount(paymentDTO.getAmount())
                .impUid(paymentDTO.getImpUid())
                // paymentDate는 @PrePersist를 통해 자동으로 현재 시간으로 설정됩니다.
                .build();
        paymentRepository.save(paymentEntity);

        String memberId = paymentDTO.getMemberId(); // PaymentDTO에 memberId를 추가했다고 가정합니다.
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("허용하지 않은 회원 입니다: " + memberId));

        CartEntity cartEntity = member.getCartEntity();

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

            ProductEntity productEntity = productRepository.findById(product.getSerialNumber())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. 상품번호: " + product.getSerialNumber()));

            OrderItemEntity orderItemEntity = OrderItemEntity.builder()
                    .order(savedOrder)
                    .product(productRepository.findById(product.getSerialNumber())
                            .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다. 상품번호: " + product.getSerialNumber() )))
                    .quantity(1) // 필요에 따라 수량 설정
                    .build();
            orderItemRepository.save(orderItemEntity);

            cartItemRepository.deleteByCartEntityAndProductEntity(cartEntity, productEntity);
        }
    }

    @Override
    public List<OrderInfoDTO> getOrdersByCurrentLoggedInUser(HttpSession session) {
        String memberId = (String) session.getAttribute("id");
        List<OrderEntity> orders = orderRepository.findByMemberId(memberId);

        List<OrderInfoDTO> orderInfoDTOs = new ArrayList<>();
        for (OrderEntity order : orders) {
            for (OrderItemEntity orderItem : order.getOrderItems()) {
                OrderInfoDTO dto = new OrderInfoDTO();
                dto.setPaymentId(order.getPayment().getPaymentId());
                dto.setSerialNumber(orderItem.getProduct().getSerialNumber());
                dto.setImpUid(order.getPayment().getImpUid());
                dto.setProductName(orderItem.getProduct().getProductName());
                dto.setQuantity(orderItem.getQuantity());
                dto.setAmount(order.getPayment().getAmount());
                dto.setPaymentDate(order.getPayment().getPaymentDate());
                orderInfoDTOs.add(dto);
            }
        }
        return orderInfoDTOs;
    }

    @Override
    public boolean cancelPayment(String impUid, String reason) {
        // PortOne API를 통해 결제 취소 요청
        String accessToken = getAccessToken();
        System.out.println(accessToken);

        String url = "https://api.iamport.kr/payments/cancel";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        Map<String, Object> body = new HashMap<>();
        body.put("imp_uid", impUid);
        body.put("reason", reason);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);

        return response.getStatusCode() == HttpStatus.OK;
    }


}
