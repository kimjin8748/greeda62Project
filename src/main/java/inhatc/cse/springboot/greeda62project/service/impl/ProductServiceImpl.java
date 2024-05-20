package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.*;
import inhatc.cse.springboot.greeda62project.repository.PotRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductRepository;
import inhatc.cse.springboot.greeda62project.repository.SetRepository;
import inhatc.cse.springboot.greeda62project.repository.SucculentRepository;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final PotRepository potRepository;
    private final SucculentRepository succulentRepository;
    private final SetRepository setRepository;

    @Override
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        List<ProductDTO> productDTOs = productEntities.stream().map(productEntity -> {
            ProductDTO dto = new ProductDTO();
            dto.setSerialNumber(productEntity.getSerialNumber());
            dto.setProductName(productEntity.getProductName());
            dto.setProductSize(productEntity.getProductSize());
            dto.setProductPrice(productEntity.getProductPrice());
            dto.setProductDescription(productEntity.getProductDescription());
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

    @Override
    public ProductDTO findById(String serialNumber) {
        Optional<ProductEntity> entityOptional = productRepository.findById(serialNumber);
        if (entityOptional.isPresent()) {
            ProductEntity productEntity = entityOptional.get();
            return ProductDTO.toProductDTO(productEntity);
        } else {
            return null;
        }
    }

    public Page<ProductEntity> findProductByCategoryPaginated(Class<?> categoryClass, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findByType(categoryClass, pageable);
    }

    @Override
    public List<ProductEntity> searchByKeyword(String keyword) {
        return productRepository.findByKeyword("%" + keyword + "%");
    }

    @Override
    public ProductDTO productView(String productId){

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Number:" + productId));

        if (productEntity != null) {
            return ProductDTO.toProductDTO(productEntity);
        }  else {
            throw new IllegalArgumentException("상품이 없습니다. :" + productId);
        }
    }

}
