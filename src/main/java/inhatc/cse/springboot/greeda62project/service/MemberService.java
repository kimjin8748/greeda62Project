package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;

public interface MemberService {

    MemberDTO saveMember(String id, String name, String email, String address);

}
