package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*주문 정보 처리 Repository*/
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findByMemberId(String memberId);
}
