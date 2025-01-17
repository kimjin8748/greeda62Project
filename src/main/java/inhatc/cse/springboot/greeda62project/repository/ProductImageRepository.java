package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ProductImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageEntity, Long> {

    ProductImageEntity findByProduct_SerialNumberAndIsMain(String serialNumber, boolean isMain);

    List<ProductImageEntity> findByProduct_SerialNumber(String serialNumber);

}
