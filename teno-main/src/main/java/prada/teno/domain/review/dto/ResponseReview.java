package prada.teno.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prada.teno.domain.review.domain.Review;

import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class ResponseReview {

    @Schema(description = "review id(pk)", example = "1")
    private long id;

    @Schema(description = "센터이름", example = "마루공원")
    private String centerName;

    @Schema(description = "댓글 내용", example = "이 코트 너무너무 좋아요")
    private String content;

    @Schema(description = "별점", example = "3")
    private int grade;

    @Schema(description = "리뷰 최초 등록 시간", example = "2022-05-20 14:25:30")
    private String uploadTime;

    @Schema(description = "리뷰 수정 시간", example = "2022-05-21 15:30:21")
    private String updateTime;

    @Builder
    public ResponseReview(long id, String centerName, String content, int grade, String uploadTime, String updateTime) {
        this.id = id;
        this.centerName = centerName;
        this.content = content;
        this.grade = grade;
        this.uploadTime = uploadTime;
        this.updateTime = updateTime;
    }

    public static ResponseReview fromEntity(Review review) {
        return ResponseReview.builder()
                .id(review.getId())
                .centerName(review.getCenter().getName())
                .content(review.getContent())
                .grade(review.getGrade())
                .uploadTime(review.getUploadTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .updateTime(review.getUpdateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
}
