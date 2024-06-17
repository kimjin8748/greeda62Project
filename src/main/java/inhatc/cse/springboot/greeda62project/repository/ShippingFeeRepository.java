package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingFeeRepository extends JpaRepository<ShippingFeeEntity, Integer> { //배송비 정보 처리 Repository
    ShippingFeeEntity findByMembershipLevelEntity_Id(Long membershipLevelId);
}
