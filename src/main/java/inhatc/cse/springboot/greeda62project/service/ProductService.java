package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.PotDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.dto.SetDTO;
import inhatc.cse.springboot.greeda62project.dto.SucculentDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    List<PotDTO> findPotProducts();

    List<SucculentDTO> findSucProducts();

    List<SetDTO> findSetProducts();
}
