package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.CartEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.repository.CartRepository;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*회원정보 처리 Service 메소드 구현*/
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;

    /*회원가입 수행항 정보 DB에 저장하는 Service 로직*/
    @Override
    public MemberDTO saveMember(String id, String password, String name, String email, String address) {
        MemberEntity memberEntity = MemberEntity.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .address(address)
                .build();

        memberEntity = memberRepository.save(memberEntity); // memberRepository를 사용

        // 장바구니 엔티티 생성 및 저장
        CartEntity cartEntity = new CartEntity();
        cartEntity.setMemberEntity(memberEntity);
        cartRepository.save(cartEntity);

        MemberDTO memberDTO = new MemberDTO(memberEntity.getId(), memberEntity.getPassword(), memberEntity.getName(),
                memberEntity.getEmail(), memberEntity.getAddress());

        return memberDTO;
    }

    /*회원정보 DB에서 확인후 로그인 수행 Service 로직*/
    @Override
    public MemberDTO login(MemberDTO memberDTO) {
        Optional <MemberEntity> byMemberid = memberRepository.findById(memberDTO.getId());
        if(byMemberid.isPresent()){
            MemberEntity memberEntity = byMemberid.get(); // Optional에서 꺼냄
            if(memberEntity.getPassword().equals(memberDTO.getPassword())){
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /*아이디 중복 체크 Service 로직*/
    @Override
    public boolean checkIdDuplicated(String id) {
        return memberRepository.existsById(id);
    }

    /*본인의 회원정보를 DB에서 확인후 아이디 찾는 Service 로직*/
    @Override
    public MemberDTO forgot(MemberDTO memberDTO) {
        Optional <MemberEntity> byMembername = memberRepository.findByName(memberDTO.getName());
        if(byMembername.isPresent()){
            MemberEntity memberEntity = byMembername.get(); // Optional에서 꺼냄
            if(memberEntity.getEmail().equals(memberDTO.getEmail())){
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /*회원정보를 DB에서 가져오는 Service 로직*/
    @Override
    public MemberDTO findMemberById(String id) {
        Optional<MemberEntity> memberEntityOptional = memberRepository.findById(id);
        if (memberEntityOptional.isPresent()) {
            MemberEntity memberEntity = memberEntityOptional.get();
            return MemberDTO.toMemberDTO(memberEntity);
        } else {
            return null; // 또는 예외 처리
        }
    }

    /*회원정보 수정 Service 로직*/
    @Override
    public boolean updateMember(String id, String password, String name, String email, String address) {
        // 해당 ID의 MemberEntity를 찾음
        Optional<MemberEntity> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            MemberEntity memberEntity = optionalMember.get();
            // 엔티티의 필드 값 업데이트
            memberEntity.setPassword(password);
            memberEntity.setName(name);
            memberEntity.setEmail(email);
            memberEntity.setAddress(address);
            // 리포지토리를 통해 엔티티 저장
            memberRepository.save(memberEntity);
            return true;
        }
        return false;
    }

    /*회원정보 삭제 Service 로직*/
    @Override
    @Transactional
    public boolean deleteMember(String id) {
        Optional<MemberEntity> memberOptional = memberRepository.findById(id);
        if (memberOptional.isPresent()) {
            memberRepository.deleteById(id);
            memberRepository.flush();
            return true;
        } else {
            return false;
        }
    }

    /*회원정보 keyword로 DB에서 검색하는 Service 로직*/
    @Override
    public Page<MemberDTO> findByMember(String keyword, Pageable pageable) {
        Page<MemberEntity> memberEntities;
        if (keyword == null || keyword.isEmpty()) {
            memberEntities = memberRepository.findAll(pageable);
        } else {
            memberEntities = memberRepository.findByMemberId("%" + keyword + "%", pageable);
        }

        List<MemberDTO> memberDTOs = memberEntities.stream()
                .map(MemberDTO::toMemberDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(memberDTOs, pageable, memberEntities.getTotalElements());
    }

}
