package prada.teno.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import prada.teno.domain.user.domain.User;

@Getter
public class RequestCreateUser {

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "비밀번호", type = "string")
    private String userPassword;

    @Schema(description = "닉네임", type = "string")
    private String nickName;

    public User toEntity() {
        return User.builder()
                .email(email)
                .userPassword(userPassword)
                .nickName(nickName)
                .build();
    }
}
