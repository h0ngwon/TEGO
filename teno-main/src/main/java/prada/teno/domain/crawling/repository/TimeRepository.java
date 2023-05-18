package prada.teno.domain.crawling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prada.teno.domain.crawling.domain.Time;

@Repository
public interface TimeRepository extends JpaRepository<Time, Long> {
}
