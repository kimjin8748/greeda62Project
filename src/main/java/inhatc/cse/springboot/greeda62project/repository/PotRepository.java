package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PotRepository extends JpaRepository <PotEntity, String> {

}
