package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;
import inhatc.cse.springboot.greeda62project.repository.BoardRepository;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.repository.MembershipLevelRepository;
import inhatc.cse.springboot.greeda62project.repository.ShippingFeeRepository;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import inhatc.cse.springboot.greeda62project.service.MembershipService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipLevelRepository membershipLevelRepository;
    private final ShippingFeeRepository shippingFeeRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<MembershipLevelEntity> getAllMembershipLevels() {
        return membershipLevelRepository.findAll();
    }

    @Override
    public ShippingFeeEntity getShippingFeeByMembershipLevel(Long membershipLevelId) {
        return shippingFeeRepository.findByMembershipLevelEntity_Id(membershipLevelId);
    }

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
