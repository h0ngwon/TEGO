package prada.teno.domain.crawling.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prada.teno.domain.crawling.domain.Court;

@Repository
public interface CourtRepository extends JpaRepository<Court, Long> {
    Court findByNameAndCenterId(String name, Long Id);
}
