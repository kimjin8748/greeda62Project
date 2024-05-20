package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SucculentRepository extends JpaRepository <SucculentEntity, String> {

    @Query("SELECT su FROM SucculentEntity su WHERE " +
            "su.productName LIKE %:keyword% OR " +
            "su.productDescription LIKE %:keyword%" )
    List<SucculentEntity> findBySucculentKeyword(@Param("keyword") String keyword);
}
