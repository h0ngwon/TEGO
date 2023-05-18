package prada.teno.domain.review.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.crawling.repository.CenterRepository;
import prada.teno.domain.review.dto.RequestAddReview;
import prada.teno.domain.review.dto.RequestUpdateReview;
import prada.teno.domain.review.dto.ResponseReview;
import prada.teno.domain.review.domain.Review;
import prada.teno.domain.review.repository.ReviewRepository;
import prada.teno.domain.user.domain.User;
import prada.teno.domain.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private CenterRepository centerRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;

    public ReviewService(CenterRepository centerRepository, UserRepository userRepository, ReviewRepository reviewRepository) {
        this.centerRepository = centerRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public ResponseReview add(
            RequestAddReview requestAddReview
    ) {

        Center center;
        User user;

        try {
            center = centerRepository.getById(requestAddReview.getCenterId());               // getById -> Center 객체를 넣지 않아도 저장에 문제 없음
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no center");
        }
        try {
            user = userRepository.getById(requestAddReview.getUserId());               // getById -> Center 객체를 넣지 않아도 저장에 문제 없음
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("no user");
        }

        Review review = Review.builder()
                .center(center)
                .user(user)
                .content(requestAddReview.getContent())
                .grade(requestAddReview.getGrade())
                .uploadTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        return ResponseReview.fromEntity(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public List<ResponseReview> findReviews(
            long centerId
    ) {
        Center center = centerRepository.findById(centerId)
                .orElseThrow(() -> new NoSuchElementException("no center"));

        return center.getReviews()
                .stream()
                .map(ResponseReview::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseReview update(
            long id, RequestUpdateReview requestUpdateReview
    ) {

        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("no review"));

        review.setContent(requestUpdateReview.getContent());
        review.setGrade(requestUpdateReview.getGrade());
        review.setUpdateTime(LocalDateTime.now());

        return ResponseReview.fromEntity(review);
    }

    @Transactional
    public void delete(
            long id
    ) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("no review"));

        reviewRepository.delete(review);
    }
}
