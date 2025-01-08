package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/*회원정보 처리 Service 메소드 선언*/
public interface MemberService {

    /*회원가입 수행항 정보 DB에 저장하는 Service 로직*/
    MemberDTO saveMember(String id, String password, String name, String email, String address);

    /*회원정보 DB에서 확인후 로그인 수행 Service 로직*/
    MemberDTO login(MemberDTO memberDTO);

    /*아이디 중복 체크 Service 로직*/
    boolean checkIdDuplicated(String id);

    /*본인의 회원정보를 DB에서 확인후 아이디 찾는 Service 로직*/
    MemberDTO forgot(MemberDTO memberDTO);

    /*회원정보를 DB에서 가져오는 Service 로직*/
    MemberDTO findMemberById(String id);

    /*회원정보 수정 Service 로직*/
    boolean updateMember(String id, String password, String name, String email, String address);

    /*회원정보 삭제 Service 로직*/
    boolean deleteMember(String id);

    /*회원정보 keyword로 DB에서 검색하는 Service 로직*/
    Page<MemberDTO> findByMember(String keyword, Pageable pageable);
}
