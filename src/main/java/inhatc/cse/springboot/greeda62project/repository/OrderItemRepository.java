package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.OrderItemEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/*주문 상품 처리 Repository*/
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {

    boolean existsByProduct(ProductEntity productEntity);

    boolean existsByProductSerialNumber(String product);
}
