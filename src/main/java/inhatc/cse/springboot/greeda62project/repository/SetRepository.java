package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SetRepository extends JpaRepository <SetEntity, String> {
    Optional<SetEntity> findById(String serialNumber);
}
