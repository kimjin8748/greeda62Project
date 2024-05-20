package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface ProductService {

    Page<ProductDTO> findAllProducts(Pageable pageable);

    Page<ProductEntity> findProductByCategoryPaginated(Class<?> categoryClass, int pageNo, int pageSize);

    List<PotEntity> searchByPotKeyword(String keyword);

    List<SucculentEntity> searchBySucculentKeyword(String keyword);

    List<SetEntity> searchBySetKeyword(String keyword);

    ProductDTO findById(String serialNumber);

//    ProductDTO productView(String productId);

}
