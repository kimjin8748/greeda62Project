package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import lombok.*;

import java.time.LocalDate;

/*게시판 로직 처리 위한 DTO*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardDTO {

    private int board_id;
    private String boardText;
    private String boardTitle;
    private LocalDate boardDate;
    private MemberEntity member;
    private String maskedMemberId;
    private String adminComment;

    public BoardDTO(int board_id, String boardText, String boardTitle, LocalDate boardDate, MemberEntity member) {
        this.board_id = board_id;
        this.boardText = boardText;
        this.boardTitle = boardTitle;
        this.boardDate = boardDate;
        this.member = member;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO dto = new BoardDTO();
        dto.setBoard_id(boardEntity.getBoard_id());
        dto.setBoardText(boardEntity.getBoardText());
        dto.setBoardTitle(boardEntity.getBoardTitle());
        dto.setBoardDate(boardEntity.getBoardDate());
        dto.setMember(boardEntity.getMember());
        dto.setAdminComment(boardEntity.getAdminComment());
        return dto;
    }

    public void setMaskedMemberId() {
        if (this.member != null && this.member.getId() != null) {
            String id = this.member.getId();
            if (id.length() > 4) {
                this.maskedMemberId = id.substring(0, 4) + "*".repeat(id.length() - 4);
            } else {
                this.maskedMemberId = id;
            }
        }
    }

}