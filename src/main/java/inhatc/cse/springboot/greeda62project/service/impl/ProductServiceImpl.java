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
    public List<ProductDTO> findAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            ProductDTO dto = new ProductDTO();

            // PotEntity 정보 설정
            Optional.ofNullable(productEntity.getPotEntity()).ifPresent(pot -> {
                dto.setPotSerialNumber(pot.getSerialNumber());
                dto.setPotProductName(pot.getProductName());
                dto.setPotProductSize(pot.getProductSize());
                dto.setPotProductPrice(pot.getProductPrice());
                dto.setPotProductDescription(pot.getProductDescription());
            });

            // SucculentEntity 정보 설정
            Optional.ofNullable(productEntity.getSucculentEntity()).ifPresent(succulent -> {
                dto.setSucculentSerialNumber(succulent.getSerialNumber());
                dto.setSucculentProductName(succulent.getProductName());
                dto.setSucculentProductSize(succulent.getProductSize());
                dto.setSucculentProductPrice(succulent.getProductPrice());
                dto.setSucculentProductDescription(succulent.getProductDescription());
            });

            // SetEntity 정보 설정
            Optional.ofNullable(productEntity.getSetEntity()).ifPresent(set -> {
                dto.setSetSerialNumber(set.getSerialNumber());
                dto.setSetProductName(set.getProductName());
                dto.setSetProductSize(set.getProductSize());
                dto.setSetProductPrice(set.getProductPrice());
                dto.setSetProductDescription(set.getProductDescription());
            });

            productDTOs.add(dto);
        }
        return productDTOs;
    }

    @Override
    public PotDTO findPotById(String serialNumber) {
        Optional<PotEntity> potEntityOptional = potRepository.findById(serialNumber);
        if (potEntityOptional.isPresent()) {
            PotEntity productEntity = potEntityOptional.get();
            return PotDTO.toPotDTO(productEntity);
        } else {
            return null;
        }
    }

    @Override
    public SucculentDTO findSucculentById(String serialNumber) {
        Optional<SucculentEntity> succulentEntityOptional = succulentRepository.findById(serialNumber);
        if (succulentEntityOptional.isPresent()) {
            SucculentEntity succulentEntity = succulentEntityOptional.get();
            return SucculentDTO.toSucculentDTO(succulentEntity);
        } else {
            return null;
        }
    }

    @Override
    public SetDTO findSetById(String serialNumber) {
        Optional<SetEntity> setEntityOptional = setRepository.findById(serialNumber);
        if (setEntityOptional.isPresent()) {
            SetEntity setEntity = setEntityOptional.get();
            return SetDTO.toSetDTO(setEntity);
        } else {
            return null;
        }
    }

    @Override
    public Page<PotEntity> findPotPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return potRepository.findAll(pageable);
    }

    @Override
    public Page<SucculentEntity> findSucculentPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return succulentRepository.findAll(pageable);
    }

    @Override
    public Page<SetEntity> findSetPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return setRepository.findAll(pageable);
    }

    @Override
    public List<PotEntity> searchByPotKeyword(String keyword) {
        return potRepository.findByPotKeyword("%" + keyword + "%");
    }

    @Override
    public List<SucculentEntity> searchBySucculentKeyword(String keyword) {
        return succulentRepository.findBySucculentKeyword("%" + keyword + "%");
    }

    @Override
    public List<SetEntity> searchBySetKeyword(String keyword) {
        return setRepository.findByPotKeyword("%" + keyword + "%");
    }

    public ProductDTO productView(String productId){

        ProductEntity productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Number:" + productId));

        if (productEntity.getPotEntity() != null) {
            return convertToDTO(productEntity.getPotEntity());
        } else if (productEntity.getSucculentEntity() != null) {
            return convertToDTO(productEntity.getSucculentEntity());
        } else if (productEntity.getSetEntity() != null) {
            return convertToDTO(productEntity.getSetEntity());
        } else {
            throw new IllegalArgumentException("상품이 없습니다. :" + productId);
        }
    }

    private ProductDTO convertToDTO(Object productEntity){
        ProductDTO productDTO = new ProductDTO();

        if (productEntity instanceof PotEntity) {
            PotEntity pot = (PotEntity) productEntity;
            productDTO.setPotSerialNumber(pot.getSerialNumber());
            productDTO.setPotProductName(pot.getProductName());
            productDTO.setPotProductSize(pot.getProductSize());
            productDTO.setPotProductPrice(pot.getProductPrice());
            productDTO.setPotProductDescription(pot.getProductDescription());
        } else if (productEntity instanceof SucculentEntity) {
            SucculentEntity succulent = (SucculentEntity) productEntity;
            productDTO.setSucculentSerialNumber(succulent.getSerialNumber());
            productDTO.setSucculentProductName(succulent.getProductName());
            productDTO.setSucculentProductSize(succulent.getProductSize());
            productDTO.setSucculentProductPrice(succulent.getProductPrice());
            productDTO.setSucculentProductDescription(succulent.getProductDescription());
        } else if (productEntity instanceof SetEntity) {
            SetEntity set = (SetEntity) productEntity;
            productDTO.setSetSerialNumber(set.getSerialNumber());
            productDTO.setSetProductName(set.getProductName());
            productDTO.setSetProductSize(set.getProductSize());
            productDTO.setSetProductPrice(set.getProductPrice());
            productDTO.setSetProductDescription(set.getProductDescription());
        }

        return productDTO;
    }


}
