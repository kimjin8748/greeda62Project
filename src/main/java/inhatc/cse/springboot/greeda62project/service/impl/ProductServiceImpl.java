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

    @Override
    public Page<ProductDTO> findAllProducts(Pageable pageable) {
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        List<ProductDTO> productDTOs = productEntities.stream()
                .map(ProductDTO::toProductDTO)
                .collect(Collectors.toList());

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
    public ProductDTO productView(String productId) {

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Number:" + productId));

        if (productEntity != null) {
            return ProductDTO.toProductDTO(productEntity);
        } else {
            throw new IllegalArgumentException("상품이 없습니다. :" + productId);
        }
    }

    @Override
    public boolean checkIdDuplicated(String serialNumber) {
        return productRepository.existsById(serialNumber);
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductEntity productEntity = null;

        // 카테고리 값을 상품 타입으로 변환
        String productType = convertCategoryToProductType(productDTO.getProductType());

        if (productDTO.getSerialNumber() == null || productDTO.getSerialNumber().isEmpty()) {
            throw new IllegalArgumentException("serialNumber cannot be null or empty.");
        }
        // 상품 타입에 따라 다른 엔티티로 변환
        if ("POT".equals(productType)) {
            productEntity = new PotEntity(
                    productDTO.getSerialNumber(),
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription()
            );
        } else if ("SUC".equals(productType)) {
            productEntity = new SucculentEntity(
                    productDTO.getSerialNumber(),
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription()
            );
        } else if ("SET".equals(productType)) {
            productEntity = new SetEntity(
                    productDTO.getSerialNumber(),
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription()
            );
        }

        productEntity = productRepository.save(productEntity);

        return new ProductDTO(
                productEntity.getSerialNumber(),
                productEntity.getProductName(),
                productEntity.getProductSize(),
                productEntity.getProductPrice(),
                productEntity.getProductDescription()
        );
    }

    // 카테고리 값을 상품 타입으로 변환하는 메서드
    private String convertCategoryToProductType(String category) {
        if (category == null) {
            throw new IllegalArgumentException("Category is null");
        }
        switch (category) {
            case "다육이":
                return "SUC";
            case "화분":
                return "POT";
            case "다육이&화분 SET":
                return "SET";
            default:
                throw new IllegalArgumentException("Invalid category: " + category);
        }
    }

}


