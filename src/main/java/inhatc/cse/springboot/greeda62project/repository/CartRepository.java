package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<CartEntity, Integer> { //장바구니 정보 처리 Repository
    Optional<CartEntity> findByMemberEntity_Id(String memberId);

}
