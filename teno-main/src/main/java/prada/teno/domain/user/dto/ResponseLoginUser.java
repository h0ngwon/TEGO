package prada.teno.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ResponseLoginUser {

    @Schema(description = "user 정보")
    private ResponseUser responseUser;

    @Schema(description = "토큰")
    private String jwtToken;

    @Builder
    public ResponseLoginUser(ResponseUser responseUser, String jwtToken) {
        this.responseUser = responseUser;
        this.jwtToken = jwtToken;
    }
}
