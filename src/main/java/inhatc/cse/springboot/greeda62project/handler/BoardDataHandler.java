package inhatc.cse.springboot.greeda62project.handler;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import org.springframework.stereotype.Service;

@Service
public interface BoardDataHandler {
    BoardEntity saveBoardEntity(String board_id, String b_text, String b_title, String b_date);

    BoardEntity updateBoardEntity(String board_id, String b_text, String b_title, String b_date);

}
