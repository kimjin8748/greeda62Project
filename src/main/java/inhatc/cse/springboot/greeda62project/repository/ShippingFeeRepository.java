package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*배송비 정보 처리 Repository*/
public interface ShippingFeeRepository extends JpaRepository<ShippingFeeEntity, Integer> {
    ShippingFeeEntity findByMembershipLevelEntity_Id(Long membershipLevelId);
}
