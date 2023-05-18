package prada.teno.domain.crawling.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.review.domain.Review;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@NoArgsConstructor
public class ResponseCenter {

    @Schema(description = "센터 pk", example = "1")
    private long id;

    @Schema(description = "센터 이름", example = "마루공원")
    private String name;

    @Schema(description = "주소", example = "서울특별시 강남구 개포로 625(일원동)")
    private String address;

    @Schema(description = "지역구", example = "강남구")
    private String district;

    @Schema(description = "센터시작시간", example = "06:00:00")
    private String centerStartTime;

    @Schema(description = "센터종료시간", example = "22:00:00")
    private String centerEndTime;

    @Schema(description = "댓글 갯수", example = "5")
    private int reviewCount;

    @Schema(description = "평균 별점", example = "3.25")
    private double averageGrade;

    @Builder
    public ResponseCenter(long id, String name, String address, String district
            , String centerStartTime, String centerEndTime, int reviewCount, double averageGrade) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.district = district;
        this.centerStartTime = centerStartTime;
        this.centerEndTime = centerEndTime;
        this.reviewCount = reviewCount;
        this.averageGrade = averageGrade;
    }

    public static ResponseCenter fromEntity(Center center) {
        return ResponseCenter.builder()
                .id(center.getId())
                .name(center.getName())
                .address(center.getAddress())
                .district(center.getDistrict())
                .centerStartTime(center.getCenterStartTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .centerEndTime(center.getCenterEndTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .reviewCount(center.getReviews().size())        // getReviews 때문에 발생
                .averageGrade(getAverageGrade(center.getReviews()))
                .build();
    }

    public static double getAverageGrade(List<Review> reviews) {
        double sum = 0;
        if (reviews.size() == 0) {
            return 0.0;
        }
        for (Review review : reviews) {
            sum += review.getGrade();
        }
        return Math.round(sum / reviews.size() * 100) / 100.0;
    }
}
