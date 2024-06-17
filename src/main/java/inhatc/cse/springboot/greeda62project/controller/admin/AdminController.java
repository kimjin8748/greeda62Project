package inhatc.cse.springboot.greeda62project.controller.admin;

import inhatc.cse.springboot.greeda62project.dto.BoardDTO;
import inhatc.cse.springboot.greeda62project.dto.MemberDTO;
import inhatc.cse.springboot.greeda62project.dto.ProductDTO;
import inhatc.cse.springboot.greeda62project.entity.MemberEntity;
import inhatc.cse.springboot.greeda62project.entity.ProductEntity;
import inhatc.cse.springboot.greeda62project.service.BoardService;
import inhatc.cse.springboot.greeda62project.service.MemberService;
import inhatc.cse.springboot.greeda62project.service.ProductService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final MemberService memberService;
    private final BoardService boardService;

    @Autowired
    private Environment env;

    private String getUploadDir() {
        return env.getProperty("upload.path");
    }

    //관리자 페이지 이동 로직
    @GetMapping("/admin")
    public String admin(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String id = (String) session.getAttribute("id");

        if (id == null) {
            session.setAttribute("isAdmin", false);
            return "redirect:/"; // 세션에 id가 없으면 홈으로 리디렉션, "isAdmin" 세션 속성을 false로 설정
        }

        MemberDTO memberDTO = memberService.findUser(id);

        if (memberDTO == null || !memberDTO.getId().equals("admin")) {
            session.setAttribute("isAdmin", false);
            return "redirect:/"; // 사용자가 null이거나 관리자가 아니면 홈으로 리디렉션, "isAdmin" 세션 속성을 false로 설정
        }

        session.setAttribute("isAdmin", true); // 관리자 여부를 세션에 추가
        return "admin/admin"; // 관리자 페이지로 이동
    }


    //관리자페이지 회원관리 메뉴 이동 로직
    @GetMapping("/admin/memberCTR")
    public String memberControl(Model model){
        List<MemberDTO> members = memberService.findAllMembers();
        model.addAttribute("members", members);
        return "admin/memberCTR";
    }

    //상품 조회 메뉴 이동 로직
    @GetMapping("/admin/productCheck")
    public String listProducts(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                               @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                               Model model) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<ProductDTO> page = productService.findAllProducts(pageable);
        List<ProductDTO> products = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products", products);

        return "admin/product_check"; // 상품 목록을 보여주는 View 이름
    }

    //상품 등록 메뉴 이동 로직
    @GetMapping("/admin/productInsert")
    public String productInsert(){
        return "admin/product_insert";
    }

    //상품 등록 로직
    @PostMapping("/admin/productInsert")
    public String productInsert(@ModelAttribute ProductDTO productDTO, Model model, RedirectAttributes redirectAttributes) {
        // 필드 검증
        if (productDTO.getSerialNumber() == null || productDTO.getProductType() == null ||
                productDTO.getProductName() == null || productDTO.getProductSize() == null ||
                productDTO.getProductPrice() <= 0 || productDTO.getProductDescription() == null ||
                productDTO.getProductPhoto().isEmpty()) {
            model.addAttribute("insertError", "모든 필드를 채워야 합니다.");
            model.addAttribute("insertFailed", true);
            return "admin/product_insert";
        }

        // 중복 확인
        boolean isDuplicated = productService.checkIdDuplicated(productDTO.getSerialNumber());
        if (isDuplicated) {
            model.addAttribute("numberError", "상품 번호가 이미 있습니다");
            model.addAttribute("numberFailed", true);
            return "admin/product_insert";
        } else {
            // 파일 저장
            MultipartFile file = productDTO.getProductPhoto();
            if (!file.isEmpty()) {
                try {
                    String uploadDir = getUploadDir();
                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(uploadDir, file.getOriginalFilename());
                    Files.write(path, bytes);
                    productDTO.setPhotoFileName(file.getOriginalFilename());
                } catch (IOException e) {
                    e.printStackTrace();
                    model.addAttribute("fileUploadError", "파일 업로드 실패");
                    return "admin/product_insert";
                }
            }
            // 상품 저장
            productService.saveProduct(productDTO);
            redirectAttributes.addFlashAttribute("insertSuccess", "상품 등록이 성공적으로 완료되었습니다.");
            return "redirect:/admin/productCheck";
        }
    }

    //상품별 상품 수정, 삭제 페이지 이동 로직
    @GetMapping("/admin/productUpdate/{id}")
    public String productUpdateForm(@PathVariable("id") String serialNumber, Model model){
        ProductDTO productDTO = productService.findById(serialNumber);
        model.addAttribute("product", productDTO);
        return "admin/product_update";
    }

    //상품 수정, 삭제 로직
    @PostMapping("/admin/productUpdate")
    public String updateProduct(@RequestParam("action") String action, ProductDTO productDTO, RedirectAttributes redirectAttributes) {

        if("update".equals(action)) {
            boolean updateResult = productService.updateProduct(productDTO);
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

    //상품 검색 페이지 이동 로직
    @GetMapping("/searchProduct")
    public String searchProducts(@RequestParam(required = false) String keyword, Model model) {
        List<ProductEntity> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productService.searchByProduct(keyword);
        } else {
            // keyword가 없는 경우의 처리 로직
            products = new ArrayList<>();
        }

        // ProductEntity 리스트를 ProductDTO 리스트로 변환
        List<ProductDTO> productDTOSs = products.stream().map(ProductDTO::toProductDTO).collect(Collectors.toList());

        model.addAttribute("products", productDTOSs);
        model.addAttribute("isEmpty", products.isEmpty());
        model.addAttribute("keyword", keyword);

        return "admin/searchProduct"; // 검색 결과를 보여줄 페이지의 이름
    }

    //회원 검색 페이지 이동 로직
    @GetMapping("/searchMember")
    public String searchMembers(@RequestParam(required = false) String keyword, Model model) {
        List<MemberEntity> members;
        if (keyword != null && !keyword.isEmpty()) {
            members = memberService.findByMember(keyword);
        } else {
            // keyword가 없는 경우의 처리 로직
            members = new ArrayList<>();
        }

        // ProductEntity 리스트를 ProductDTO 리스트로 변환
        List<MemberDTO> memberDTOs = members.stream().map(MemberDTO::toMemberDTO).collect(Collectors.toList());

        model.addAttribute("members", memberDTOs);
        model.addAttribute("isEmpty", members.isEmpty());
        model.addAttribute("keyword", keyword);

        return "admin/searchMember"; // 검색 결과를 보여줄 페이지의 이름
    }

    //게시판 관리 메뉴 이동 로직
    @GetMapping("/admin/boardCheck")
    public String faq(Model model) {
        List<BoardDTO> boardList = boardService.findAllBoard();
        model.addAttribute("boards", boardList);
        return "admin/adminFaq";
    }
    
    //게시판 글로 이동 로직
    @GetMapping("/admin/edit/{title}")
    public String faqEdit(@PathVariable("title") String boardTitle, Model model) {
        BoardDTO boardDTO = boardService.findByBoardTitle(boardTitle);
        model.addAttribute("boards", boardDTO);
        return "admin/adminFaqEdit";
    }

    //게시판 답글 달기 로직
    @PostMapping("/admin/addComment")
    public String addAdminComment(@RequestParam int boardId, @RequestParam String adminComment) {
        boardService.addAdminComment(boardId, adminComment);
        return "redirect:/admin/boardCheck";
    }

}
