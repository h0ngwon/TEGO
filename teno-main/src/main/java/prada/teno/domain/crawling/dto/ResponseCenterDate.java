package prada.teno.domain.crawling.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCenterDate {

    @Schema(description = "날짜", type = "string", example = "20")
    private String date;

    @Schema(description = "예약가능건수", type = "int", example = "5")
    private int reservableCount;

    @Schema(description = "전체건수", type = "int", example = "22")
    private int totalCount;
}
