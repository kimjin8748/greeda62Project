package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/*게시판 정보 처리 위한 Repository*/
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    Optional<BoardEntity> findByBoardTitle(String boardTitle);

    @Query("SELECT b FROM BoardEntity b WHERE " +
            "b.boardTitle LIKE %:keyword%")
    List<BoardEntity> findByKeyword(@Param("keyword") String keyword);
}
