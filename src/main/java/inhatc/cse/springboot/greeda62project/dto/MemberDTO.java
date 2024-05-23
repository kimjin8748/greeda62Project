package inhatc.cse.springboot.greeda62project.dto;

import inhatc.cse.springboot.greeda62project.entity.MemberEntity;

import lombok.*;

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
    private String maskedMemberId;

    public MemberDTO(String id, String password, String name, String email, String address) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO dto = new MemberDTO();
        dto.setId(memberEntity.getId());
        dto.setPassword(memberEntity.getPassword());
        dto.setName(memberEntity.getName());
        dto.setEmail(memberEntity.getEmail());
        dto.setAddress(memberEntity.getAddress());
        return dto;
    }

    public void setMaskedMemberId() {
            if (id.length() > 4) {
                this.maskedMemberId = id.substring(0, 4) + "*".repeat(id.length() - 4);
            } else {
                this.maskedMemberId = id;
            }

    }

}
