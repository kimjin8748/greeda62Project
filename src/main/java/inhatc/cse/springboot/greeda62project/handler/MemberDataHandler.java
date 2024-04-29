package inhatc.cse.springboot.greeda62project.handler;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

public interface MemberDataHandler {

    MemberEntity saveMemberEntity(String id, String password, String name, String email, String address);

    MemberEntity updateMemberEntity(String id, String password, String name, String email, String address);
}
