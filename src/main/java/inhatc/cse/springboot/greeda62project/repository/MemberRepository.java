package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/*회원정보 처리 Repository*/
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    Optional <MemberEntity> findById(String id);

    Optional <MemberEntity> findByName(String name);

    @Query("SELECT m FROM MemberEntity m WHERE " +
            "m.id LIKE %:keyword%")
    List<MemberEntity> findByMemberId(@Param("keyword") String keyword);
}
