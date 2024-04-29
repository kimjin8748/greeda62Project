package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.handler.MemberDataHandler;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    MemberDataHandler memberDataHandler;
    MemberRepository memberRepository;

    @Autowired
    public MemberServiceImpl(MemberDataHandler memberDataHandler, MemberRepository memberRepository) {
        this.memberDataHandler = memberDataHandler;
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberDTO saveMember(String id, String password, String name, String email, String address) {
        MemberEntity memberEntity = memberDataHandler.saveMemberEntity(id, password, name, email, address);

        MemberDTO memberDTO = new MemberDTO(memberEntity.getId(), memberEntity.getPassword(), memberEntity.getName(),
                memberEntity.getEmail(), memberEntity.getAddress());

        return memberDTO;
    }

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

    @Override
    public boolean checkIdDuplicated(String id) {
        return memberRepository.existsById(id);
    }

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

    //회원정보 수정 페이지로 이동을 위한 로직
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

}
