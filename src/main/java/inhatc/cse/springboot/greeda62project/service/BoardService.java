package inhatc.cse.springboot.greeda62project.service;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/*게시판 정보 처리 위한 Service 메소드 선언*/
public interface BoardService {

    /*게시판의 특정 글을 찾는 Service 로직*/
    BoardDTO findByBoardTitle(String boardTitle);

    /*작성한 글 DB에 저장하는 Service 로직*/
    BoardDTO saveBoard(int board_id, String b_text, String b_title, LocalDate b_date, String memberId);

    /*관리자가 답글 단 내용 DB에 저장하는 Service 로직*/
    void addAdminComment(int boardId, String adminComment);

    /*글 내용 수정하는 Service 로직*/
    boolean updateProduct(BoardDTO boardDTO);

    /*글 삭제하는 Service 로직*/
    boolean deleteProduct(BoardDTO boardDTO);

    /*문의글 검색 Service 로직*/
    Page<BoardDTO> searchByBoard(String keyword, Pageable pageable);
}
