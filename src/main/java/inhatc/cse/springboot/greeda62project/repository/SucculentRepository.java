package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucculentRepository extends JpaRepository <SucculentEntity, String> {

}