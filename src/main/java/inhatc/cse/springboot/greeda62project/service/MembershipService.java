package inhatc.cse.springboot.greeda62project.service;


import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;

import java.util.List;

/*멤버쉽 정보 처리 Service 메소드 선언*/
public interface MembershipService {

    /*DB에 있는 멤버쉽 정보 가져오는 Service 로직*/
    List<MembershipLevelEntity> getAllMembershipLevels();

    /*멤버쉽 레벨별 배송비 정보 DB에서 가져오는 Service 로직*/
    ShippingFeeEntity getShippingFeeByMembershipLevel(Long membershipLevelId);

    /*회원별 멤버쉽 레벨 정보 DB에서 가져오는 Service 로직*/
    MembershipLevelEntity getUserMembershipLevel(String id);
}
