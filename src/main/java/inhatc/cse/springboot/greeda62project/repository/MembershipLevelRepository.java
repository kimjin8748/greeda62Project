package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*멤버쉽 정보 처리 Repository*/
public interface MembershipLevelRepository extends JpaRepository<MembershipLevelEntity, Integer> {
}
