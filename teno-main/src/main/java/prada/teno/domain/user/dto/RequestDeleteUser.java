package prada.teno.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RequestDeleteUser {

    @Schema(description = "유저 비밀번호", type = "string")
    private String password;
}
