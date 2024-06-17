package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class BoardEntity { //게시판 정보 Entity

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int board_id;

    private String boardText;
    private String boardTitle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate boardDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    private String adminComment;

    @PrePersist
    public void prePersist() {
        this.boardDate = (this.boardDate == null) ? LocalDate.now() : this.boardDate;
    }

}
