package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

import lombok.*;

/*회원 로직 처리 위한 DTO*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberDTO {


    private String id;
    private String password;
    private String name;
    private String email;
    private String address;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO dto = new MemberDTO();
        dto.setId(memberEntity.getId());
        dto.setPassword(memberEntity.getPassword());
        dto.setName(memberEntity.getName());
        dto.setEmail(memberEntity.getEmail());
        dto.setAddress(memberEntity.getAddress());
        return dto;
    }

}
