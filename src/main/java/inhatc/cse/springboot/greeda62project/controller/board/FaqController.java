package inhatc.cse.springboot.greeda62project.controller.board;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FaqController {

    private final BoardService boardService;

    @GetMapping("/faq")
    public String faq(Model model) {
        List<BoardDTO> BoardList = boardService.findAllBoard();
        model.addAttribute("board", BoardList);
        return "/board/faq";
    }

    @GetMapping("/faq/new")
    public String faqNew() {
        return "/board/faqNew";
    }

    @PostMapping("/faq/new")
    public String createBoard(@ModelAttribute BoardDTO boardDTO) {

        boardService.saveBoard(boardDTO.getBoard_id(), boardDTO.getB_text(),
                boardDTO.getB_title(), boardDTO.getB_date());

        return "/board/faq";
    }
}
