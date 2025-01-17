package inhatc.cse.springboot.greeda62project.controller.admin;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final MemberService memberService;
    private final BoardService boardService;

    /*관리자 페이지 이동 로직*/
    @GetMapping("/admin")
    public String admin(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String id = (String) session.getAttribute("id");

        if (id == null) {
            session.setAttribute("isAdmin", false);
            return "redirect:/"; // 세션에 id가 없으면 홈으로 리디렉션, "isAdmin" 세션 속성을 false로 설정
        }

        MemberDTO memberDTO = memberService.findMemberById(id);

        if (memberDTO == null || !memberDTO.getId().equals("admin")) {
            session.setAttribute("isAdmin", false);
            return "redirect:/"; // 사용자가 null이거나 관리자가 아니면 홈으로 리디렉션, "isAdmin" 세션 속성을 false로 설정
        }

        session.setAttribute("isAdmin", true); // 관리자 여부를 세션에 추가
        return "admin/admin"; // 관리자 페이지로 이동
    }


    /*관리자페이지 회원관리 메뉴 이동 로직*/
    @GetMapping("/admin/memberCTR")
    public String memberControl(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                @RequestParam(required = false) String keyword,
                                Model model) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<MemberDTO> page = memberService.findByMember(keyword, pageable);
        List<MemberDTO> members = page.getContent();

        model.addAttribute("members", members);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("isEmpty", members.isEmpty());
        model.addAttribute("keyword", keyword);

        return "admin/memberCTR";
    }

    /*상품 조회 메뉴 이동 로직*/
    @GetMapping("/admin/productCheck")
    public String listProducts(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               @RequestParam(required = false) String keyword,
                               Model model) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> page = productService.findAllProducts(keyword, pageable);
        List<ProductDTO> products = page.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("isEmpty", products.isEmpty());
        model.addAttribute("keyword", keyword);

        return "admin/product_check"; // 상품 목록을 보여주는 View 이름
    }

    /*상품 등록 메뉴 이동 로직*/
    @GetMapping("/admin/productInsert")
    public String productInsert(){
        return "admin/product_insert";
    }

    /*상품 등록 로직*/
    @PostMapping("/admin/productInsert")
    public String productInsert(@ModelAttribute ProductDTO productDTO,
                                @RequestParam("productPhotos") List<MultipartFile> productPhotos,
                                Model model, RedirectAttributes redirectAttributes) {
        productDTO.setSerialNumber("null");
        // 필드 검증
        if (productDTO.getProductType() == null || productDTO.getProductName() == null || productDTO.getProductSize() == null ||
                productDTO.getProductPrice() <= 0 || productDTO.getProductDescription() == null ||
                productPhotos.isEmpty()) {
            model.addAttribute("insertError", "모든 필드를 채워야 합니다.");
            model.addAttribute("insertFailed", true);
            return "admin/product_insert";
        }

        // 파일 저장 및 상품 등록 서비스 호출
        try {
            productService.saveProduct(productDTO, productPhotos);
            redirectAttributes.addFlashAttribute("insertSuccess", "상품 등록이 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            model.addAttribute("fileUploadError", "파일 업로드 중 오류가 발생했습니다.");
            return "admin/product_insert";
        }

        return "redirect:/admin/productCheck";

    }

    /*상품별 상품 수정, 삭제 페이지 이동 로직*/
    @GetMapping("/admin/productUpdate/{id}")
    public String productUpdateForm(@PathVariable("id") String serialNumber, Model model){
        ProductDTO productDTO = productService.findById(serialNumber);
        model.addAttribute("product", productDTO);
        return "admin/product_update";
    }

    /*상품 수정, 삭제 로직*/
    @PostMapping("/admin/productUpdate")
    public String updateProduct(@RequestParam("action") String action,
                                ProductDTO productDTO,
                                @RequestParam("productPhotos") List<MultipartFile> productPhotos,
                                RedirectAttributes redirectAttributes) {
        if("update".equals(action)) {
            boolean updateResult = productService.updateProduct(productDTO, productPhotos);
            if (updateResult) {
                redirectAttributes.addFlashAttribute("updateSuccess", "상품 정보가 성공적으로 수정되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("updateError", "상품 정보 수정에 실패하였습니다.");
            }
        } else if("delete".equals(action)){
            boolean deleteResult = productService.deleteProduct(productDTO);
            if (deleteResult) {
                redirectAttributes.addFlashAttribute("deleteSuccess", "상품이 성공적으로 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("deleteError", "상품 삭제에 실패하였습니다.");
            }
        }
        return "redirect:/admin/productCheck";
    }

    /*게시판 관리 메뉴 이동 로직*/
    @GetMapping("/admin/boardCheck")
    public String faq(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                      @RequestParam(required = false) String keyword,
                      Model model) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<BoardDTO> page = boardService.searchByBoard(keyword, pageable);
        List<BoardDTO> boards = page.getContent();

        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("isEmpty", boards.isEmpty());
        model.addAttribute("keyword", keyword);

        return "admin/adminFaq";
    }

    /*게시판 글로 이동 로직*/
    @GetMapping("/admin/edit/{title}")
    public String faqEdit(@PathVariable("title") String boardTitle, Model model) {
        BoardDTO boardDTO = boardService.findByBoardTitle(boardTitle);
        model.addAttribute("boards", boardDTO);
        return "admin/adminFaqEdit";
    }

    /*게시판 답글 달기 로직*/
    @PostMapping("/admin/addComment")
    public String addAdminComment(@RequestParam int boardId, @RequestParam String adminComment, RedirectAttributes redirectAttributes) {
        boardService.addAdminComment(boardId, adminComment);
        redirectAttributes.addFlashAttribute("commentSuccess", "답글이 성공적으로 작성되었습니다.");
        return "redirect:/admin/boardCheck";
    }

}
