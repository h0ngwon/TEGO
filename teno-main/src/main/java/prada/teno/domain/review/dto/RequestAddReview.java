package prada.teno.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(title = "RequestAddReviewDto", description = "review 등록 DTO")
@Getter
public class RequestAddReview {

    @Schema(name = "user id", description = "유저 테이블의 pk", example = "1")
    private long userId;

    @Schema(name = "center id", description = "센터 테이블의 pk", example = "1")
    private long centerId;

    @Schema(name = "content", description = "리뷰 내용", maxLength = 300)
    private String content;

    @Schema(name = "grade", description = "별점", example = "1", allowableValues = {"1", "2", "3", "4", "5"})
    private int grade;
}
