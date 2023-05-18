package prada.teno.global;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseMessage {

    @Schema(description = "내용")
    private String message;

    @Builder
    public ResponseMessage(String message) {
        this.message = message;
    }
}
