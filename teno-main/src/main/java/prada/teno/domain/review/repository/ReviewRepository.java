package prada.teno.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prada.teno.domain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
