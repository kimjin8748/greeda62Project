package inhatc.cse.springboot.greeda62project.controller;

import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;

public class ConvertToProductDTO {

    public static ProductDTO convertToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();

        if (productEntity.getPotEntity() != null) {
            productDTO.setPotSerialNumber(productEntity.getPotEntity().getSerialNumber());
            productDTO.setPotProductName(productEntity.getPotEntity().getProductName());
            productDTO.setPotProductSize(productEntity.getPotEntity().getProductSize());
            productDTO.setPotProductPrice(productEntity.getPotEntity().getProductPrice());
            productDTO.setPotProductDescription(productEntity.getPotEntity().getProductDescription());
        }

        if (productEntity.getSucculentEntity() != null) {
            productDTO.setSucculentSerialNumber(productEntity.getSucculentEntity().getSerialNumber());
            productDTO.setSucculentProductName(productEntity.getSucculentEntity().getProductName());
            productDTO.setSucculentProductSize(productEntity.getSucculentEntity().getProductSize());
            productDTO.setSucculentProductPrice(productEntity.getSucculentEntity().getProductPrice());
            productDTO.setSucculentProductDescription(productEntity.getSucculentEntity().getProductDescription());
        }

        if (productEntity.getSetEntity() != null) {
            productDTO.setSetSerialNumber(productEntity.getSetEntity().getSerialNumber());
            productDTO.setSetProductName(productEntity.getSetEntity().getProductName());
            productDTO.setSetProductSize(productEntity.getSetEntity().getProductSize());
            productDTO.setSetProductPrice(productEntity.getSetEntity().getProductPrice());
            productDTO.setSetProductDescription(productEntity.getSetEntity().getProductDescription());
        }

        return productDTO;
    }
}
