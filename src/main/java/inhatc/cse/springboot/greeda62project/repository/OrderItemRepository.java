package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.OrderEntity;
import inhatc.cse.springboot.greeda62project.entity.OrderItemEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    boolean existsByProduct(ProductEntity productEntity);

    boolean existsByProductSerialNumber(String product);
}
