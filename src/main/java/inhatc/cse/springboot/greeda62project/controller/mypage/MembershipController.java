package inhatc.cse.springboot.greeda62project.controller.mypage;

import inhatc.cse.springboot.greeda62project.entity.MembershipLevelEntity;
import inhatc.cse.springboot.greeda62project.entity.ShippingFeeEntity;
import inhatc.cse.springboot.greeda62project.service.MembershipService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    //멤버쉽 페이지 이동 로직
    @GetMapping("/membership")
    public String memberShip(HttpSession session, Model model){
        String id = (String) session.getAttribute("id");
        if (id == null) {
            return "redirect:/member";
        }
        List<MembershipLevelEntity> membershipLevels = membershipService.getAllMembershipLevels();
        Map<String, String> membershipBenefits = new HashMap<>();

        for (MembershipLevelEntity level : membershipLevels) {
            ShippingFeeEntity shippingFee = membershipService.getShippingFeeByMembershipLevel(level.getId());
            membershipBenefits.put(level.getLevelName(), "배송비 " + shippingFee.getFee() + "원 적용");
        }

        // 현재 사용자의 멤버쉽 정보 가져오기
        MembershipLevelEntity currentUserMembershipLevel = null;
        ShippingFeeEntity currentUserShippingFee = null;

        try {
            currentUserMembershipLevel = membershipService.getUserMembershipLevel(id);
            if (currentUserMembershipLevel != null) {
                currentUserShippingFee = membershipService.getShippingFeeByMembershipLevel(currentUserMembershipLevel.getId());
            }
        } catch (IllegalStateException e) {
            // 예외 처리: 사용자에게 멤버십 레벨이 없는 경우
            model.addAttribute("error", "현재 사용자는 멤버십 레벨이 없습니다.");
        }

        model.addAttribute("membershipLevels", membershipLevels);
        model.addAttribute("membershipBenefits", membershipBenefits);
        model.addAttribute("currentUserMembershipLevel", currentUserMembershipLevel);
        model.addAttribute("currentUserShippingFee", currentUserShippingFee);

        return "mypage/membership";
    }
}
