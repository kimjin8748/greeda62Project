package inhatc.cse.springboot.greeda62project.service;


import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;

import java.util.List;

public interface MembershipService {

    List<MembershipLevelEntity> getAllMembershipLevels();

    ShippingFeeEntity getShippingFeeByMembershipLevel(Long membershipLevelId);

    MembershipLevelEntity getUserMembershipLevel(String id);
}
