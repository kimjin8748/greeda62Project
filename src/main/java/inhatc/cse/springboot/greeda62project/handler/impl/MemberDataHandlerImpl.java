package inhatc.cse.springboot.greeda62project.handler.impl;

import inhatc.cse.springboot.greeda62project.dao.MemberDAO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.handler.MemberDataHandler;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MemberDataHandlerImpl implements MemberDataHandler {

    MemberDAO memberDAO;
    @Autowired
    public MemberDataHandlerImpl(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    @Override
    public MemberEntity saveMemberEntity(String id, String password, String name, String email, String address) {
        MemberEntity memberEntity = new MemberEntity(id, password, name, email, address);
        return memberDAO.saveMember(memberEntity);
    }

}
