package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SucculentRepository extends JpaRepository <SucculentEntity, String> {
    Optional<SucculentEntity> findById(String serialNumber);
}
