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
    Optional<ProductEntity> findByPotEntity_serialNumber(String potId);
}
