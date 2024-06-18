package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*결제 정보 처리 Repository*/
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {

}
