package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository <ProductEntity, String> {

//    JPQL로 연관되어 있는 다른 엔티티에 접근하여 검색하는 기능
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "p.potEntity.productName LIKE %:keyword% OR " +
            "p.potEntity.productDescription LIKE %:keyword% OR " +
            "p.succulentEntity.productName LIKE %:keyword% OR " +
            "p.succulentEntity.productDescription LIKE %:keyword% OR " +
            "p.setEntity.productName LIKE %:keyword% OR " +
            "p.setEntity.productDescription LIKE %:keyword%")
    List<ProductEntity> findByKeyword(@Param("keyword") String keyword);


}
