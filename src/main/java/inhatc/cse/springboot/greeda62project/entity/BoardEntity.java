package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class BoardEntity {
    @Id
    private String board_id;
    private String b_text;
    private String b_title;
    private String b_date;

}
