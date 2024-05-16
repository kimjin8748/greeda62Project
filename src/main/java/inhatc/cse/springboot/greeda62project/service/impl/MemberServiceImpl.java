package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.handler.MemberDataHandler;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDataHandler memberDataHandler;
    private final MemberRepository memberRepository;

    //회원가입을 위한 로직
    @Override
    public MemberDTO saveMember(String id, String password, String name, String email, String address) {
        MemberEntity memberEntity = memberDataHandler.saveMemberEntity(id, password, name, email, address);

        MemberDTO memberDTO = new MemberDTO(memberEntity.getId(), memberEntity.getPassword(), memberEntity.getName(),
                memberEntity.getEmail(), memberEntity.getAddress());

        return memberDTO;
    }

    //로그인을 위한 로직
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

    //아이디 중복 체크 로직
    @Override
    public boolean checkIdDuplicated(String id) {
        return memberRepository.existsById(id);
    }

    //아이디 찾기를 위한 로직
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

    //관리자가 DB의 회원정보를 모두 볼 수 있게 하는 로직
    @Override
    public List<MemberDTO> findAllMembers() {
        List<MemberEntity> members = memberRepository.findAll();
        return members.stream().map(MemberDTO::toMemberDTO).collect(Collectors.toList());
    }


    //DB의 회원정보를 수정하는 로직
    @Override
    public boolean updateMember(String id, String password, String name, String email, String address) {
        MemberEntity memberEntity = memberDataHandler.updateMemberEntity(id, password, name, email, address);
        if (memberEntity != null) {
            MemberDTO memberDTO = new MemberDTO(memberEntity.getId(), memberEntity.getPassword(), memberEntity.getName(),
                    memberEntity.getEmail(), memberEntity.getAddress());
            return true;
        }
        return false;
    }

    //DB의 회원을 삭제하는 로직
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

    //회원 한명을 찾는 로직
    @Override
    public MemberDTO findUser(String id) {
        Optional<MemberEntity> memberById = memberRepository.findById(id);
        if(memberById.isPresent()){
            MemberEntity memberEntity = memberById.get();
            return MemberDTO.toMemberDTO(memberEntity);
        } else {
            return null;
        }
    }

}
