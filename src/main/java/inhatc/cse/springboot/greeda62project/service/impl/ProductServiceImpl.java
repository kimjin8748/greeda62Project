package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.*;
import inhatc.cse.springboot.greeda62project.repository.OrderItemRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductRepository;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*상품 정보 처리 Service 메소드 구현*/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    /*모든 상품 정보 DB에서 가져오는 Service 로직*/
    @Override
    public Page<ProductDTO> findAllProducts(String keyword, Pageable pageable) {
        Page<ProductEntity> productEntities;
        if (keyword == null || keyword.isEmpty()) {
            productEntities = productRepository.findAll(pageable);
        } else {
            productEntities = productRepository.findByKeyword(keyword, pageable);
        }
        List<ProductDTO> productDTOs = productEntities.stream()
                .map(ProductDTO::toProductDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

    /*상품 정보 DB에서 가져오는 Service 로직*/
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

    /*페이징 처리 로직*/
    public Page<ProductEntity> findProductByCategoryPaginated(Class<?> categoryClass, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return productRepository.findByType(categoryClass, pageable);
    }

    /*장바구니에 상품을 넣기 위해 상품 검색 Service 로직 */
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

    /*상품 등록시 상품 번호 중복 확인 Service 로직*/
    @Override
    public boolean checkIdDuplicated(String serialNumber) {
        return productRepository.existsById(serialNumber);
    }

    /*상품 등록 Service 로직*/
    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        ProductEntity productEntity = null;

        // 카테고리 값을 상품 타입으로 변환
        String productType = convertCategoryToProductType(productDTO.getProductType());
        // 시리얼 넘버 생성
        String SerialNumber = generateSerialNumber(productType);

        // 상품 타입에 따라 다른 엔티티로 변환
        if ("POT".equals(productType)) {
            productEntity = new PotEntity(
                    SerialNumber,
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription(),
                    productDTO.getImageUrl(),
                    productDTO.getPhotoFileName()
            );
        } else if ("SUC".equals(productType)) {
            productEntity = new SucculentEntity(
                    SerialNumber,
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription(),
                    productDTO.getImageUrl(),
                    productDTO.getPhotoFileName()
            );
        } else if ("SET".equals(productType)) {
            productEntity = new SetEntity(
                    SerialNumber,
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription(),
                    productDTO.getImageUrl(),
                    productDTO.getPhotoFileName()
            );
        }

        productEntity = productRepository.save(productEntity);

        return new ProductDTO(
                productEntity.getSerialNumber(),
                productEntity.getProductName(),
                productEntity.getProductSize(),
                productEntity.getProductPrice(),
                productEntity.getProductDescription(),
                productEntity.getImageUrl(),
                productEntity.getPhotoFileName()
        );
    }

    /*상품 수정 Service 로직*/
    @Override
    @Transactional
    public boolean updateProduct(ProductDTO productDTO) {
        try {
            // DTO에서 ID를 이용하여 데이터베이스에서 상품 엔티티를 찾습니다.
            ProductEntity productEntity = productRepository.findById(productDTO.getSerialNumber())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. 상품번호 = " + productDTO.getSerialNumber()));

            // 상품 엔티티의 데이터를 업데이트합니다.
            productEntity.setProductName(productDTO.getProductName());
            productEntity.setProductPrice(productDTO.getProductPrice());
            productEntity.setProductDescription(productDTO.getProductDescription());
            productEntity.setProductSize(productDTO.getProductSize());

            // 변경 감지(dirty checking) 기능으로 인해 트랜잭션이 종료될 때
            // 변경된 데이터가 데이터베이스에 자동으로 반영됩니다 (save 호출 필요 없음).

            return true;
        } catch (Exception e) {
            // 예외가 발생하면 false를 반환합니다.
            return false;
        }
    }

    /*상품 삭제 Service 로직*/
    @Override
    @Transactional
    public boolean deleteProduct(ProductDTO productDTO) {
        try {
            ProductEntity productEntity = productRepository.findById(productDTO.getSerialNumber())
                    .orElseThrow(() -> new IllegalArgumentException("상품이 없습니다. 상품번호 = " + productDTO.getSerialNumber()));

            productRepository.delete(productEntity);

            return true;
        } catch (Exception e) {
            System.out.println("상품 삭제중 오류 발생" + e);
            return false;
        }
    }

    /*주문한 상품인지 확인하는 Service 로직*/
    @Override
    public boolean isProductPurchased(String  productId) {
        // orderItemRepository를 통해 구매된 상품인지 확인
        return orderItemRepository.existsByProductSerialNumber(productId);
    }

    /*카테고리 값을 상품 타입으로 변환하는 로직*/
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

    /*상품 시리얼넘버 생성 로직*/
    private String generateSerialNumber(String productType) {
        String latestId = null;
        int nextSequence = 1;

        switch (productType) { //타입별로 가장 높은 ID 가져오기
            case "SUC":
                latestId = productRepository.findLatestSUCId();
                break;
            case "POT":
                latestId = productRepository.findLatestPOTId();
                break;
            case "SET":
                latestId = productRepository.findLatestSETId();
                break;
        }

        if(latestId != null && latestId.startsWith(productType)) {
            String[] parts = latestId.split("-");
            if(parts.length > 1) nextSequence = Integer.parseInt(parts[1]) + 1; //가져온 ID 숫자 증가시키기
            System.out.println(parts[1]);
            System.out.println(nextSequence);

        }

        return ProductEntity.generateId(productType, nextSequence); //증가시킨 번호로 ID 생성
    }

}


