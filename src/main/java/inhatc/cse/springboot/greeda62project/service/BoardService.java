package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

import java.time.LocalDate;
import java.util.List;

public interface BoardService {
    BoardDTO saveBoard(int board_id, String b_text, String b_title, LocalDate b_date, String memberId);

    List<BoardDTO> findAllBoard();
//    boolean updateBoard(String board_id, String b_text, String b_title, String b_date);
//    boolean deleteBoard(String board_id);
}
