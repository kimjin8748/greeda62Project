package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/*상품 정보 처리 Service 메소드 선언*/
public interface ProductService {

    /*모든 상품 정보 DB에서 가져오는 Service 로직*/
    Page<ProductDTO> findAllProducts(String keyword, Pageable pageable);

    /*상품 정보 DB에서 가져오는 Service 로직*/
    ProductDTO findById(String serialNumber);

    /*페이징 처리 로직*/
    Page<ProductEntity> findProductByCategoryPaginated(Class<?> categoryClass, int pageNo, int pageSize);

    /*장바구니에 상품을 넣기 위해 상품 검색 Service 로직 */
    ProductDTO productView(String productId);

    /*상품 등록시 상품 번호 중복 확인 Service 로직*/
    boolean checkIdDuplicated(String serialNumber);

    /*상품 등록 Service 로직*/
    void saveProduct(ProductDTO productDTO, List<MultipartFile> productPhotos) throws IOException;

    /*상품 수정 Service 로직*/
    boolean updateProduct(ProductDTO productDTO, List<MultipartFile> productPhotos);

    /*상품 삭제 Service 로직*/
    boolean deleteProduct(ProductDTO productDTO);

    /*주문한 상품인지 확인하는 Service 로직*/
    boolean isProductPurchased(String  productId);
}
