package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;

public interface MemberService {

    MemberDTO saveMember(String id, String password, String name, String email, String address);

    MemberDTO login(MemberDTO memberDTO);

    boolean checkIdDuplicated(String id);

    MemberDTO forgot(MemberDTO memberDTO);

    MemberDTO findMemberById(String id);

    boolean updateMember(String id, String password, String name, String email, String address);

    boolean deleteMember(String id);
}
