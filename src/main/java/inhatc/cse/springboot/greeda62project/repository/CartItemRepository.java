package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.dto.CartItemDTO;
import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItemEntity, Integer> {
    Optional<CartItemEntity> findByCartEntityAndProductEntity(CartEntity cartEntity, ProductEntity productEntity);

    List<CartItemEntity> findByCartEntityId(int cartId);

    void deleteByCartEntityAndProductEntity(CartEntity cartEntity, ProductEntity productEntity);

}

