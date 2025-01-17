package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.*;
import inhatc.cse.springboot.greeda62project.repository.OrderItemRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductImageRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductRepository;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*상품 정보 처리 Service 메소드 구현*/
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductImageRepository productImageRepository;

    private final Environment env;

    private String getUploadDir() {
        return env.getProperty("upload.path");
    }

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
                .map(productEntity -> {
                    // 대표 이미지 검색
                    ProductImageEntity mainImage = productImageRepository.findByProduct_SerialNumberAndIsMain(
                            productEntity.getSerialNumber(), true);

                    String imageUrl = mainImage != null ? mainImage.getImageUrl() : null;
                    String photoFileName = mainImage != null ? mainImage.getPhotoFileName() : null;

                    return ProductDTO.toProductDTO(productEntity, imageUrl, photoFileName);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(productDTOs, pageable, productEntities.getTotalElements());
    }

    /*상품 정보 DB에서 가져오는 Service 로직*/
    @Override
    public ProductDTO findById(String serialNumber) {
        Optional<ProductEntity> entityOptional = productRepository.findById(serialNumber);
        if (entityOptional.isPresent()) {
            ProductEntity productEntity = entityOptional.get();
            ProductImageEntity mainImage = productImageRepository.findByProduct_SerialNumberAndIsMain(
                    productEntity.getSerialNumber(), true);

            String imageUrl = mainImage != null ? mainImage.getImageUrl() : null;
            String photoFileName = mainImage != null ? mainImage.getPhotoFileName() : null;
            return ProductDTO.toProductDTO(productEntity, imageUrl, photoFileName);
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
            ProductImageEntity mainImage = productImageRepository.findByProduct_SerialNumberAndIsMain(
                    productEntity.getSerialNumber(), true);

            String imageUrl = mainImage != null ? mainImage.getImageUrl() : null;
            String photoFileName = mainImage != null ? mainImage.getPhotoFileName() : null;
            return ProductDTO.toProductDTO(productEntity, imageUrl, photoFileName);
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
    public void saveProduct(ProductDTO productDTO, List<MultipartFile> productPhotos) throws IOException {
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
                    productDTO.getProductDescription()
            );
        } else if ("SUC".equals(productType)) {
            productEntity = new SucculentEntity(
                    SerialNumber,
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription()
            );
        } else if ("SET".equals(productType)) {
            productEntity = new SetEntity(
                    SerialNumber,
                    productDTO.getProductType(),
                    productDTO.getProductName(),
                    productDTO.getProductSize(),
                    productDTO.getProductPrice(),
                    productDTO.getProductDescription()
            );
        }

        productEntity = productRepository.save(productEntity);

        // 이미지 저장 로직
        String uploadDir = getUploadDir();
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 디렉토리 생성
        }

        for (int i = 0; i < productPhotos.size(); i++) {
            MultipartFile file = productPhotos.get(i);
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                Path path = Paths.get(uploadDir, originalFilename);

                file.transferTo(path.toFile());

                ProductImageEntity productImageEntity = new ProductImageEntity();
                productImageEntity.setImageUrl("/uploads/" + originalFilename);
                productImageEntity.setPhotoFileName(originalFilename);
                productImageEntity.setProduct(productEntity);

                // 첫 번째 이미지를 대표 이미지로 설정
                productImageEntity.setMain(i == 0);

                productImageRepository.save(productImageEntity);
            }
        }
    }

    @Override
    @Transactional
    public boolean updateProduct(ProductDTO productDTO, List<MultipartFile> productPhotos) {
        try {
            // DTO에서 ID를 이용하여 데이터베이스에서 상품 엔티티를 찾습니다.
            ProductEntity productEntity = productRepository.findById(productDTO.getSerialNumber())
                    .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다. 상품번호 = " + productDTO.getSerialNumber()));

            List<ProductImageEntity> productImages = productImageRepository.findByProduct_SerialNumber(productDTO.getSerialNumber());
            String uploadDir = getUploadDir();

            // 상품 엔티티의 데이터를 업데이트합니다.
            productEntity.setProductName(productDTO.getProductName());
            productEntity.setProductPrice(productDTO.getProductPrice());
            productEntity.setProductDescription(productDTO.getProductDescription());
            productEntity.setProductSize(productDTO.getProductSize());

            // 상품 이미지 데이터 업데이트
            if (!productPhotos.isEmpty()) {
                // 기존 이미지 파일 삭제
                for (ProductImageEntity image : productImages) {
                    Path imagePath = Paths.get(uploadDir, image.getPhotoFileName());
                    try {
                        Files.deleteIfExists(imagePath); // 파일이 존재하면 삭제
                    } catch (IOException ex) {
                        System.out.println("이미지 파일 삭제 실패: " + imagePath + " - " + ex.getMessage());
                    }
                }

                // 새로운 이미지 파일 추가
                for (int i = 0; i < productPhotos.size(); i++) {
                    MultipartFile file = productPhotos.get(i);
                    if (!file.isEmpty()) {
                        String originalFilename = file.getOriginalFilename();
                        Path path = Paths.get(uploadDir, originalFilename);

                        file.transferTo(path.toFile());

                        ProductImageEntity productImageEntity = new ProductImageEntity();
                        productImageEntity.setImageUrl("/uploads/" + originalFilename);
                        productImageEntity.setPhotoFileName(originalFilename);
                        productImageEntity.setProduct(productEntity);

                        // 첫 번째 이미지를 대표 이미지로 설정
                        productImageEntity.setMain(i == 0);
                        productImageRepository.save(productImageEntity);
                    }
                }
            }

            // 상품 엔티티 업데이트 후 변경 감지(dirty checking)로 인해 트랜잭션 종료 시 변경된 데이터가 자동으로 반영됨

            return true;
        } catch (Exception e) {
            // 예외가 발생하면 false를 반환합니다.
            System.out.println("상품 수정 중 오류 발생: " + e.getMessage());
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


            List<ProductImageEntity> productImages = productImageRepository.findByProduct_SerialNumber(productDTO.getSerialNumber());
            String uploadDir = getUploadDir(); // 이미지 업로드 경로

            // 업로드된 경로에서 이미지 삭제
            for (ProductImageEntity image : productImages) {
                Path imagePath = Paths.get(uploadDir, image.getPhotoFileName());
                try {
                    Files.deleteIfExists(imagePath); // 파일이 존재하면 삭제
                } catch (IOException ex) {
                    System.out.println("이미지 파일 삭제 실패: " + imagePath + " - " + ex.getMessage());
                }
            }

            productImageRepository.deleteAll(productImages); // 상품 이미지 삭제

            productRepository.delete(productEntity); // 상품 삭제

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


