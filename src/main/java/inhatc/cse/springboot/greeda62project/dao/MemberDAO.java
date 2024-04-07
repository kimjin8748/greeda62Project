package inhatc.cse.springboot.greeda62project.dao;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

public interface MemberDAO {

    MemberEntity saveMember(MemberEntity memberEntity);

    MemberEntity getMember(String id);
}
