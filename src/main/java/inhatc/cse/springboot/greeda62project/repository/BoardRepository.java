package inhatc.cse.springboot.greeda62project.repository;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*게시판 정보 처리 위한 Repository*/
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    Optional<BoardEntity> findByBoardTitle(String boardTitle);
}
