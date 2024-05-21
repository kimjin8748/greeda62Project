package inhatc.cse.springboot.greeda62project.controller.board;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FaqNewController {
    private final BoardService boardService;
    @GetMapping("/faq/new")
    public String faqNew() {
        return "/board/faqNew";
    }

    @PostMapping("/faq/new")
    public String createBoard(BoardDTO boardDTO) {
        String board_id = boardDTO.getBoard_id();
        String b_text = boardDTO.getB_text();
        String b_title = boardDTO.getB_title();
        String b_date = boardDTO.getB_date();

        boardService.saveBoard(board_id, b_text, b_title, b_date);
        return "/board/faqNew";
    }
}
