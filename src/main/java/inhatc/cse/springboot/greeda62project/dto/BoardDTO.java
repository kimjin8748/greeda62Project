package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardDTO {

    private int board_id;
    private String b_text;
    private String b_title;
    private LocalDate b_date;
    private MemberEntity memberEntity;
    private String maskedMemberId;

    public BoardDTO(int board_id, String b_text, String b_title, LocalDate b_date, MemberEntity memberEntity) {
        this.board_id = board_id;
        this.b_text = b_text;
        this.b_title = b_title;
        this.b_date = b_date;
        this.memberEntity = memberEntity;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO dto = new BoardDTO();
        dto.setBoard_id(boardEntity.getBoard_id());
        dto.setB_text(boardEntity.getB_text());
        dto.setB_title(boardEntity.getB_title());
        dto.setB_date(boardEntity.getB_date());
        dto.setMemberEntity(boardEntity.getMember());
        return dto;
    }

    public void setMaskedMemberId() {
        if (this.memberEntity != null && this.memberEntity.getId() != null) {
            String id = this.memberEntity.getId();
            if (id.length() > 4) {
                this.maskedMemberId = id.substring(0, 4) + "*".repeat(id.length() - 4);
            } else {
                this.maskedMemberId = id;
            }
        }
    }

}