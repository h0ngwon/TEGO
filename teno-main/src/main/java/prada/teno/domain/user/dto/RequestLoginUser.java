package prada.teno.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestLoginUser {

    @Schema(description = "로그인 이메일", type = "string", example = "bluesh55@naver.com")
    private String email;

    @Schema(description = "로그인 비밀번호", type = "string")
    private String password;
}
