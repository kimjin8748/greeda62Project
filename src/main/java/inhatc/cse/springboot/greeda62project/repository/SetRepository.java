package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetRepository extends JpaRepository <SetEntity, String> {

}
