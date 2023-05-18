package prada.teno.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RequestChangePassword {

    @Schema(description = "기존 비밀번호", type = "string")
    private String password;

    @Schema(description = "변경 비밀번호", type = "string")
    private String updatePassword;
}
