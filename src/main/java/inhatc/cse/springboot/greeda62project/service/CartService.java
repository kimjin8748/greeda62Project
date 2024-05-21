package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.CartItemDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.CartItemEntity;

import java.util.List;

public interface CartService {

    void addCart(MemberDTO memberDTO, ProductDTO productDTO, int amount);

    List<CartItemEntity> getCartItemsByUserId(String memberId);

//    CartEntity findById(int id);
}
