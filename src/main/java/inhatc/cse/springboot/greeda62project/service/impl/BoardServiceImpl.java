package inhatc.cse.springboot.greeda62project.service.impl;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.repository.BoardRepository;
import inhatc.cse.springboot.greeda62project.repository.MemberRepository;
import inhatc.cse.springboot.greeda62project.service.BoardService;
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
    public BoardDTO saveBoard(int board_id, String b_text, String b_title, LocalDate b_date, String memberId) {
        MemberEntity member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + memberId));
        BoardEntity boardEntity = BoardEntity.builder()
                .board_id(board_id)
                .b_text(b_text)
                .b_title(b_title)
                .b_date(b_date)
                .member(member)
                .build();

        boardEntity = boardRepository.save(boardEntity);

        BoardDTO boardDTO = new BoardDTO(
                boardEntity.getBoard_id(),
                boardEntity.getB_text(),
                boardEntity.getB_title(),
                boardEntity.getB_date(),
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
//    @Override
//    public boolean updateBoard(String board_id, String b_text, String b_title, String b_date) {
//        BoardEntity boardEntity = boardRepository.updateBoardEntity(board_id, b_text, b_title, b_date);
//        if (boardEntity != null) {
//            BoardDTO boardDTO = new BoardDTO(
//                    boardEntity.getBoard_id(),
//                    boardEntity.getB_text(),
//                    boardEntity.getB_title(),
//                    boardEntity.getB_date()
//            );
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean deleteBoard(String board_id) {
//        Optional<BoardEntity> boardOptional = boardRepository.findById(board_id);
//        if (boardOptional.isPresent()) {
//            boardRepository.deleteById(board_id);
//            boardRepository.flush();
//            return true;
//        } else {
//            return false;
//        }
//    }
}
