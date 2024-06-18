package inhatc.cse.springboot.greeda62project.controller.board;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FaqController {

    private final BoardService boardService;

    /*고객문의 페이지 이동 로직*/
    @GetMapping("/faq")
    public String faq(Model model) {
        List<BoardDTO> boardList = boardService.findAllBoard();
        model.addAttribute("boards", boardList);
        return "/board/faq";
    }

    /*게시판의 글쓰기 페이지 이동 로직*/
    @GetMapping("/faq/new")
    public String faqNew(HttpSession session) {
        String memberId  = (String) session.getAttribute("id");
        if (memberId  == null) {
            return "redirect:/member";
        }
        return "/board/faqNew";
    }

    /*글 작성 로직*/
    @PostMapping("/faq/new")
    public String createBoard(@ModelAttribute BoardDTO boardDTO, HttpSession session) {

        String memberId  = (String) session.getAttribute("id");
        if (memberId  == null) {
            return "redirect:/member";
        }

        boardService.saveBoard(boardDTO.getBoard_id(), boardDTO.getBoardText(),
                boardDTO.getBoardTitle(), boardDTO.getBoardDate(), memberId );

        return "redirect:/faq";
    }

    /*해당 글로 이동하는 로직*/
    @GetMapping("/faq/edit/{title}")
    public String faqEdit(@PathVariable("title") String boardTitle, Model model) {
        BoardDTO boardDTO = boardService.findByBoardTitle(boardTitle);
        model.addAttribute("boards", boardDTO);
        return "board/faqEdit";
    }

    /*글내용 수정, 삭제 로직*/
    @PostMapping("/faq/update")
    public String updateProduct(@RequestParam("action") String action, BoardDTO boardDTO, RedirectAttributes redirectAttributes, HttpSession session) {
        String id = (String) session.getAttribute("id");

        if (boardDTO.getMember() == null) {
            redirectAttributes.addFlashAttribute("authError", "게시글 작성자 정보를 찾을 수 없습니다.");
        } else if (id.equals(boardDTO.getMember().getId())) {
            if ("update".equals(action)) {
                boolean updateResult = boardService.updateProduct(boardDTO);
                if (updateResult) {
                    redirectAttributes.addFlashAttribute("updateSuccess", "게시글이 성공적으로 수정되었습니다.");
                } else {
                    redirectAttributes.addFlashAttribute("updateError", "게시글 수정에 실패하였습니다.");
                }
            } else if ("delete".equals(action)) {
                boolean deleteResult = boardService.deleteProduct(boardDTO);
                if (deleteResult) {
                    redirectAttributes.addFlashAttribute("deleteSuccess", "게시글이 성공적으로 삭제되었습니다.");
                } else {
                    redirectAttributes.addFlashAttribute("deleteError", "게시글 삭제에 실패하였습니다.");
                }
            }
        } else {
            redirectAttributes.addFlashAttribute("authError", "작성자만 수정 또는 삭제할 수 있습니다.");
        }

        return "redirect:/faq";
    }
}
