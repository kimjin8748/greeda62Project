package inhatc.cse.springboot.greeda62project.controller.board;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import jakarta.servlet.http.HttpSession;
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
        List<BoardDTO> boardList = boardService.findAllBoard();
        model.addAttribute("boards", boardList);
        return "/board/faq";
    }

    @GetMapping("/faq/new")
    public String faqNew() {
        return "/board/faqNew";
    }

    @PostMapping("/faq/new")
    public String createBoard(@ModelAttribute BoardDTO boardDTO, HttpSession session) {

        String memberId  = (String) session.getAttribute("id");
        if (memberId  == null) {
            return "redirect:/member";
        }

        boardService.saveBoard(boardDTO.getBoard_id(), boardDTO.getB_text(),
                boardDTO.getB_title(), boardDTO.getB_date(), memberId );

        return "redirect:/faq";
    }
}
