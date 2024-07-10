package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.repository.BoardRepository;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*게시판 정보 처리 위한 Service 메소드 구현*/
@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /*게시판의 특정 글을 찾는 Service 로직*/
    @Override
    public BoardDTO findByBoardTitle(String boardTitle) {
        Optional<BoardEntity> boardEntity = boardRepository.findByBoardTitle(boardTitle);//타이틀로 글정보 찾기
        if (boardEntity.isPresent()) {
            BoardEntity entity = boardEntity.get();
            return BoardDTO.toBoardDTO(entity);
        } else {
            return null;
        }
    }

    /*작성한 글 DB에 저장하는 Service 로직*/
    @Override
    public BoardDTO saveBoard(int board_id, String boardText, String boardTitle, LocalDate boardDate, String memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));
        BoardEntity boardEntity = BoardEntity.builder()
                .board_id(board_id)
                .boardText(boardText)
                .boardTitle(boardTitle)
                .boardDate(boardDate)
                .member(member)
                .build();

        boardEntity = boardRepository.save(boardEntity);

        BoardDTO boardDTO = new BoardDTO(
                boardEntity.getBoard_id(),
                boardEntity.getBoardText(),
                boardEntity.getBoardTitle(),
                boardEntity.getBoardDate(),
                boardEntity.getMember()
        );

        return boardDTO;
    }

    /*전체 글목록 DB에서 불러오는 Service 로직*/
    @Override
    public List<BoardDTO> findAllBoard() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDTO> boardDTOs = boardEntities.stream().map(BoardDTO::toBoardDTO).collect(Collectors.toList());
        boardDTOs.forEach(BoardDTO::setMaskedMemberId);
        return boardDTOs;
    }

    /*관리자가 답글 단 내용 DB에 저장하는 Service 로직*/
    @Override
    public void addAdminComment(int boardId, String adminComment) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardId);
        if (boardEntityOptional.isPresent()) {
            BoardEntity boardEntity = boardEntityOptional.get();
            boardEntity.setAdminComment(adminComment);
            boardRepository.save(boardEntity);
        }
    }

    /*글 내용 수정하는 Service 로직*/
    @Override
    @Transactional
    public boolean updateProduct(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepository.findByBoardTitle(boardDTO.getBoardTitle())
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. 글제목 = " + boardDTO.getBoardTitle()));

        if(boardEntity != null){
            boardEntity.setBoardText(boardDTO.getBoardText());
            boardEntity.setBoardTitle(boardDTO.getBoardTitle());
            boardEntity.setMember(boardDTO.getMember());

            return true;
        } else {
            return false;
        }
    }

    /*글 삭제하는 Service 로직*/
    @Override
    @Transactional
    public boolean deleteProduct(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepository.findByBoardTitle(boardDTO.getBoardTitle())
                .orElseThrow(() -> new IllegalArgumentException("해당 글이 존재하지 않습니다. 글제목 = " + boardDTO.getBoardTitle()));

        if(boardEntity != null){
            boardRepository.delete(boardEntity);
            return true;
        } else {
            return false;
        }
    }

    /*문의글 검색하는 Service 로직*/
    @Override
    public List<BoardEntity> searchByBoard(String keyword) {
        return boardRepository.findByKeyword("%" + keyword + "%");
    }

}
