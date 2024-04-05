package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.Column;
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
@Table(name = "member")
public class MemberEntity {

    @Id
    private String id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String address;

}
