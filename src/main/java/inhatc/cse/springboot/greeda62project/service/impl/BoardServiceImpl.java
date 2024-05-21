package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.handler.BoardDataHandler;
import inhatc.cse.springboot.greeda62project.repository.BoardRepository;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
    private final BoardDataHandler boardDataHandler;
    private final BoardRepository boardRepository;
    @Override
    public BoardDTO saveBoard(String board_id, String b_text, String b_title, String b_date) {
        BoardEntity boardEntity = boardDataHandler.saveBoardEntity(board_id, b_text, b_title, b_date);
        BoardDTO boardDTO = new BoardDTO(
                boardEntity.getBoard_id(),
                boardEntity.getB_text(),
                boardEntity.getB_title(),
                boardEntity.getB_date()
        );
        return boardDTO;
    }
    @Override
    public boolean updateBoard(String board_id, String b_text, String b_title, String b_date) {
        BoardEntity boardEntity = boardDataHandler.updateBoardEntity(board_id, b_text, b_title, b_date);
        if (boardEntity != null) {
            BoardDTO boardDTO = new BoardDTO(
                    boardEntity.getBoard_id(),
                    boardEntity.getB_text(),
                    boardEntity.getB_title(),
                    boardEntity.getB_date()
            );
            return true;
        }
        return false;
    }
    @Override
    public boolean deleteBoard(String board_id) {
        Optional<BoardEntity> boardOptional = boardRepository.findById(board_id);
        if (boardOptional.isPresent()) {
            boardRepository.deleteById(board_id);
            boardRepository.flush();
            return true;
        } else {
            return false;
        }
    }
}
