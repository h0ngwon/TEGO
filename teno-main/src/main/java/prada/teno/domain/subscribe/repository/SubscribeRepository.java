package prada.teno.domain.subscribe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prada.teno.domain.subscribe.domain.Subscribe;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository <Subscribe, Long> {
    Subscribe findByTimeIdAndUserEmail(Long timeId, String userEmail);
    List<Subscribe> findAllByUserEmailAndSubscribe(String userId, boolean subscribe);
    Subscribe findByIdAndUserEmail(Long subscribeId, String userEmail);
}
