package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PotRepository extends JpaRepository <PotEntity, String> {

    //JPQL로 연관되어 있는 다른 엔티티에 접근하여 검색하는 기능
    @Query("SELECT p FROM PotEntity p WHERE " +
            "p.productName LIKE %:keyword% OR " +
            "p.productDescription LIKE %:keyword%" )
    List<PotEntity> findByPotKeyword(@Param("keyword") String keyword);
}
