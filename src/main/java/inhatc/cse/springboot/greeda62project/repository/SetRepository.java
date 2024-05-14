package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SetRepository extends JpaRepository <SetEntity, String> {
    Optional<SetEntity> findById(String serialNumber);

    @Query("SELECT se FROM SetEntity se WHERE " +
            "se.productName LIKE %:keyword% OR " +
            "se.productDescription LIKE %:keyword%" )
    List<SetEntity> findByPotKeyword(@Param("keyword") String keyword);
}
