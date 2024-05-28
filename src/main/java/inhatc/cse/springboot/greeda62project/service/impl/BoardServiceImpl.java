package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
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

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Override
    public BoardDTO findByBoardTitle(String boardTitle) {
        Optional<BoardEntity> boardEntity = boardRepository.findByBoardTitle(boardTitle);
        if (boardEntity.isPresent()) {
            BoardEntity entity = boardEntity.get();
            return BoardDTO.toBoardDTO(entity);
        } else {
            return null;
        }
    }

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

    @Override
    public List<BoardDTO> findAllBoard() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDTO> boardDTOs = boardEntities.stream().map(BoardDTO::toBoardDTO).collect(Collectors.toList());
        boardDTOs.forEach(BoardDTO::setMaskedMemberId);
        return boardDTOs;
    }

    @Override
    public void addAdminComment(int boardId, String adminComment) {
        Optional<BoardEntity> boardEntityOptional = boardRepository.findById(boardId);
        if (boardEntityOptional.isPresent()) {
            BoardEntity boardEntity = boardEntityOptional.get();
            boardEntity.setAdminComment(adminComment);
            boardRepository.save(boardEntity);
        }
    }

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

}
