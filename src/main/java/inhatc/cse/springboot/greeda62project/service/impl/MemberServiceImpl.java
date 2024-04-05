package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.handler.MemberDataHandler;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    MemberDataHandler memberDataHandler;

    @Autowired
    public MemberServiceImpl(MemberDataHandler memberDataHandler) {
        this.memberDataHandler = memberDataHandler;
    }

    @Override
    public MemberDTO saveMember(String id, String name, String email, String address) {
        MemberEntity memberEntity = memberDataHandler.saveMemberEntity(id, name, email, address);

        MemberDTO memberDTO = new MemberDTO(memberEntity.getId(), memberEntity.getName(),
                memberEntity.getEmail(), memberEntity.getAddress());

        return memberDTO;
    }


}
