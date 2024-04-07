package inhatc.cse.springboot.greeda62project.handler;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

public interface MemberDataHandler {

    MemberEntity saveMemberEntity(String id, String name, String email, String address);

    MemberEntity getMemberEntity(String id);

}
