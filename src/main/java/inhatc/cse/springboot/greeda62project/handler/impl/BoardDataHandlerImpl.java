package inhatc.cse.springboot.greeda62project.handler.impl;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.handler.BoardDataHandler;
import org.springframework.stereotype.Service;

@Service
public class BoardDataHandlerImpl implements BoardDataHandler {

    BoardEntity boardEntity;
    @Override
    public BoardEntity saveBoardEntity(String board_id, String b_title, String b_text, String b_date) {
        return null;
    }
    @Override
    public BoardEntity updateBoardEntity(String board_id, String b_title, String b_text, String b_date) {
        return null;
    }

}
