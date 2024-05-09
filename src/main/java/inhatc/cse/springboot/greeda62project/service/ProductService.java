package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.*;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    List<PotDTO> findPotProducts();

    List<SucculentDTO> findSucProducts();

    List<SetDTO> findSetProducts();

    PotDTO findPotById(String serialNumber);

    SucculentDTO findSucculentById(String serialNumber);

    SetDTO findSetById(String serialNumber);
}
