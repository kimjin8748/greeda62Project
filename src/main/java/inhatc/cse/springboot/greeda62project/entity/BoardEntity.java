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
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int board_id;

    private String b_text;
    private String b_title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate b_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

    @PrePersist
    public void prePersist() {
        this.b_date = (this.b_date == null) ? LocalDate.now() : this.b_date;
    }

}
