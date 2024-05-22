package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.BoardEntity;
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

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO dto = new BoardDTO();
        dto.setBoard_id(boardEntity.getBoard_id());
        dto.setB_text(boardEntity.getB_text());
        dto.setB_title(boardEntity.getB_title());
        dto.setB_date(boardEntity.getB_date());
        return dto;
    }

}