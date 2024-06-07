package inhatc.cse.springboot.greeda62project.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TokenResponse {
    private int code;
    private String message;
    private Response response;

    @Getter
    @Setter
    public static class Response {
        private String access_token;
        private long now;
        private long expired_at;

    }
}
