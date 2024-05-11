package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Page<ProductDTO> findAllProducts(Pageable pageable);

    PotDTO findPotById(String serialNumber);

    SucculentDTO findSucculentById(String serialNumber);

    SetDTO findSetById(String serialNumber);

    Page<PotEntity> findPotPaginated(int pageNo, int pageSize);

    Page<SucculentEntity> findSucculentPaginated(int pageNo, int pageSize);

    Page<SetEntity> findSetPaginated(int pageNo, int pageSize);
}
