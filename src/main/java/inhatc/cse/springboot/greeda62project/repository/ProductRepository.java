package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*상품 정보 처리 Repository*/
public interface ProductRepository extends JpaRepository <ProductEntity, String> {
    @Query("SELECT p FROM ProductEntity p WHERE TYPE(p) = :type")
    Page<ProductEntity> findByType(@Param("type") Class<?> type, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE " +
            "p.productName LIKE %:keyword% OR " +
            "p.productDescription LIKE %:keyword%")
    Page<ProductEntity> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p.serialNumber FROM ProductEntity p WHERE p.serialNumber LIKE 'SUC-%' ORDER BY p.serialNumber DESC LIMIT 1")
    String findLatestSUCId();

    @Query("SELECT p.serialNumber FROM ProductEntity p WHERE p.serialNumber LIKE 'POT-%' ORDER BY p.serialNumber DESC LIMIT 1")
    String findLatestPOTId();

    @Query("SELECT p.serialNumber FROM ProductEntity p WHERE p.serialNumber LIKE 'SET-%' ORDER BY p.serialNumber DESC LIMIT 1")
    String findLatestSETId();
}
