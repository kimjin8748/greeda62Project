package inhatc.cse.springboot.greeda62project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "member")
public class MemberEntity { //회원정보 Entity

    @Id
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoardEntity> boards = new ArrayList<>();

    @OneToOne(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private CartEntity cartEntity;

    @ManyToOne
    @JoinColumn(name = "membership_level_id")
    private MembershipLevelEntity membershipLevelEntity;
}
