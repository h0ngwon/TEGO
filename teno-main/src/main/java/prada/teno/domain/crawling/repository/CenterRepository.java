package prada.teno.domain.crawling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import prada.teno.domain.crawling.domain.Center;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository <Center, Long> {

    Center findByName(String name);
    List<Center> findByNameContains(String name);

    @Query(value = "select * from center c " +
            "left join court ct on c.id = ct.center_id " +
            "left join time t on ct.id = t.id and t.start_time between :start_time and :end_time " +
            "where c.name = :name ", nativeQuery = true)
    Optional<Center> findByNameAndStartTimeBetween(@Param("name") String name, @Param("start_time") LocalDateTime startTime, @Param("end_time") LocalDateTime endTime);
}
