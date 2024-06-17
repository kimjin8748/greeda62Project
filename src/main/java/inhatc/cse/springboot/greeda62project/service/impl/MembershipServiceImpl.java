package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.repository.MembershipLevelRepository;
import inhatc.cse.springboot.greeda62project.repository.ShippingFeeRepository;
import inhatc.cse.springboot.greeda62project.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/*멤버쉽 정보 처리 Service 메소드 구현*/
@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipLevelRepository membershipLevelRepository;
    private final ShippingFeeRepository shippingFeeRepository;
    private final MemberRepository memberRepository;

    /*DB에 있는 멤버쉽 정보 가져오는 Service 로직*/
    @Override
    public List<MembershipLevelEntity> getAllMembershipLevels() {
        return membershipLevelRepository.findAll();
    }

    /*멤버쉽 레벨별 배송비 정보 DB에서 가져오는 Service 로직*/
    @Override
    public ShippingFeeEntity getShippingFeeByMembershipLevel(Long membershipLevelId) {
        return shippingFeeRepository.findByMembershipLevelEntity_Id(membershipLevelId);
    }

    /*회원별 멤버쉽 레벨 정보 DB에서 가져오는 Service 로직*/
    @Override
    public MembershipLevelEntity getUserMembershipLevel(String id) {
        MemberEntity member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        if (member.getMembershipLevelEntity() == null) {
            throw new IllegalStateException("User does not have a membership level.");
        }
        return member.getMembershipLevelEntity();
    }

}
