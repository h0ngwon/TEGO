package prada.teno.domain.review.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prada.teno.domain.review.application.ReviewService;
import prada.teno.domain.review.dto.RequestAddReview;
import prada.teno.domain.review.dto.RequestUpdateReview;
import prada.teno.domain.review.dto.ResponseReview;
import prada.teno.global.ResponseMessage;

import java.util.List;

@Tag(name = "Review", description = "리뷰")
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "review 등록", description = "review 등록을 위한 json을 받아 db에 저장", tags = "Review")
    @PostMapping(value = "/review", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<ResponseReview> addReview(
            @RequestBody RequestAddReview requestAddReview
    ) {
        return ResponseEntity.ok(reviewService.add(requestAddReview));
    }

    @Operation(summary = "review 조회", description = "center id에 해당하는 center의 모든 review를 반환", tags = "Review")
    @GetMapping(value = "/reviews")
    public ResponseEntity<List<ResponseReview>> findReviews(
            @Parameter(description = "center id(pk)", required = true, example = "1") @RequestParam long centerId
    ) {
        return ResponseEntity.ok(reviewService.findReviews(centerId));
    }

    @Operation(summary = "review 수정", description = "review id에 해당하는 review를 수정", tags = "Review")
    @PutMapping("/review/{id}")
    public ResponseEntity<ResponseReview> updateReview(
            @Parameter(description = "review id(pk)", required = true, example = "1") @PathVariable long id,
            @Parameter(description = "review 수정 json", required = true) @RequestBody RequestUpdateReview requestUpdateReview
    ) {
        return ResponseEntity.ok(reviewService.update(id, requestUpdateReview));
    }

    @Operation(summary = "review 삭제", description = "review id에 해당하는 review를 삭제", tags = "Review")
    @DeleteMapping(value = "/review/{id}", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<ResponseMessage> deleteReview(
            @Parameter(description = "review id(pk)", required = true, example = "1") @PathVariable long id
    ) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
