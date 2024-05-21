package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;

public interface BoardService {
    BoardDTO saveBoard(String board_id, String b_text, String b_title, String b_date);
    boolean updateBoard(String board_id, String b_text, String b_title, String b_date);
    boolean deleteBoard(String board_id);
}
