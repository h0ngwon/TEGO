package prada.teno.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prada.teno.domain.user.domain.User;

@NoArgsConstructor
@Getter
public class ResponseUser {

    @Schema(description = "user id(pk)")
    private long id;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Builder
    public ResponseUser(long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static ResponseUser fromEntity(User user) {
        return ResponseUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickName())
                .build();
    }
}
