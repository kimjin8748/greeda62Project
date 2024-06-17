package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Integer> { //주문 정보 처리 Repository

    List<OrderEntity> findByMemberId(String memberId);
}
