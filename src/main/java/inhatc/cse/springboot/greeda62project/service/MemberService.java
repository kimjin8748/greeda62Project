package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

public interface MemberService {

    MemberDTO saveMember(String id, String password, String name, String email, String address);

    MemberDTO getMemberEntity(String id);

    MemberDTO login(MemberDTO memberDTO);
}
