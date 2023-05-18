package prada.teno.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class RequestUpdateReview {

    @Schema(description = "리뷰 내용", type = "string", example = "왕! 여기 너무 좋아용!")
    private String content;

    @Schema(description = "별점", type = "int", example = "3")
    private int grade;
}
