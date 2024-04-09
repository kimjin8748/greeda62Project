package inhatc.cse.springboot.greeda62project.dao.impl;

import inhatc.cse.springboot.greeda62project.dao.MemberDAO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberDAOImpl implements MemberDAO {

    MemberRepository memberRepository;
    @Autowired
    public MemberDAOImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MemberEntity saveMember(MemberEntity memberEntity) {
        memberRepository.save(memberEntity);
        return memberEntity;
    }

}
