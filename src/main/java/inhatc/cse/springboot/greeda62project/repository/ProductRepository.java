package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <ProductEntity, String> {
    Page<ProductEntity> findAll(Pageable pageable);
    @Query("SELECT p FROM ProductEntity p WHERE TYPE(p) = :type")
    Page<ProductEntity> findByType(@Param("type") Class<?> type, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE " +
            "p.productName LIKE %:keyword% OR " +
            "p.productDescription LIKE %:keyword%")
    List<ProductEntity> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT p FROM ProductEntity p WHERE " +
            "p.serialNumber LIKE %:keyword% OR " +
            "p.productName LIKE %:keyword% OR " +
            "p.productDescription LIKE %:keyword% OR " +
            "p.productSize LIKE %:keyword% ")
    List<ProductEntity> findBySerialNumber(@Param("keyword") String keyword);
}
