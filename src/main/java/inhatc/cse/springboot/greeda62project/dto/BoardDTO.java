package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BoardDTO {
    private String board_id;
    private String b_text;
    private String b_title;
    private String b_date;

    public static BoardDTO toBoardDTO(BoardDTO boardDTO) {
        BoardDTO dto = new BoardDTO();
        dto.setBoard_id(boardDTO.getBoard_id());
        dto.setB_text(boardDTO.getB_text());
        dto.setB_title(boardDTO.getB_title());
        dto.setB_date(boardDTO.getB_date());
        return dto;
    }
}