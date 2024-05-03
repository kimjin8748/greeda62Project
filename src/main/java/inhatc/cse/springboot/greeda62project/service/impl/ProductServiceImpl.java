package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.*;
import inhatc.cse.springboot.greeda62project.entity.PotEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.entity.SetEntity;
import inhatc.cse.springboot.greeda62project.entity.SucculentEntity;
import inhatc.cse.springboot.greeda62project.repository.PotRepository;
import inhatc.cse.springboot.greeda62project.repository.ProductRepository;
import inhatc.cse.springboot.greeda62project.repository.SetRepository;
import inhatc.cse.springboot.greeda62project.repository.SucculentRepository;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        List<ProductEntity> products = productRepository.findAll();
        return products.stream().map(ProductDTO::toProductDTO).collect(Collectors.toList());
    }

    @Override
    public List<PotDTO> findPotProducts() {
        List<PotEntity> pots = potRepository.findAll();
        return pots.stream().map(PotDTO::toPotDTO).collect(Collectors.toList());
    }

    @Override
    public List<SucculentDTO> findSucProducts() {
        List<SucculentEntity> Succulents = succulentRepository.findAll();
        return Succulents.stream().map(SucculentDTO::toSucculentDTO).collect(Collectors.toList());
    }

    @Override
    public List<SetDTO> findSetProducts() {
        List<SetEntity> Sets = setRepository.findAll();
        return Sets.stream().map(SetDTO::toSetDTO).collect(Collectors.toList());
    }
}
