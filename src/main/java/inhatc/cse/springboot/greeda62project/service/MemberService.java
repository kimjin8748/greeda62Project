package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

import java.util.List;

public interface MemberService {

    MemberDTO saveMember(String id, String password, String name, String email, String address);

    MemberDTO login(MemberDTO memberDTO);

    boolean checkIdDuplicated(String id);

    MemberDTO forgot(MemberDTO memberDTO);

    MemberDTO findMemberById(String id);

    List<MemberDTO> findAllMembers();

    boolean updateMember(String id, String password, String name, String email, String address);

    boolean deleteMember(String id);
}
