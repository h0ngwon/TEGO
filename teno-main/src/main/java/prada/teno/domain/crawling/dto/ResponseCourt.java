package prada.teno.domain.crawling.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseCourt {

    @Schema(description = "코트 이름", example = "(A,B,C..) or (1,2,3...) or (야외)")
    private String court;

    @Schema(description = "시간 목록")
    private List<Time> timeList;
}
