package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

import java.time.LocalDate;
import java.util.List;

public interface BoardService {
    BoardDTO findByBoardTitle(String boardTitle);

    BoardDTO saveBoard(int board_id, String b_text, String b_title, LocalDate b_date, String memberId);

    List<BoardDTO> findAllBoard();

    void addAdminComment(int boardId, String adminComment);

    boolean updateProduct(BoardDTO boardDTO);

    boolean deleteProduct(BoardDTO boardDTO);
}
