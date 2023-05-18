package prada.teno.domain.crawling.domain;

import lombok.*;
import prada.teno.domain.review.domain.Review;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Table(name = "center")
@NoArgsConstructor
@Entity
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CenterType type;

    private String address;

    private String district;

    private LocalTime centerStartTime;

    private LocalTime centerEndTime;

    private LocalTime playTime;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Court> courts;

    @OneToMany(mappedBy = "center", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Builder
    public Center(long id, String name, String address, String district, LocalTime centerStartTime, LocalTime centerEndTime, LocalTime playTime, List<Court> courts, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.district = district;
        this.centerStartTime = centerStartTime;
        this.centerEndTime = centerEndTime;
        this.playTime = playTime;
        this.courts = courts;
        this.reviews = reviews;
    }

    public boolean containCourt(String courtName) {
        return courts.stream().anyMatch((court) -> court.getName().equals(courtName));
    }

    public void addCourts(List<Court> courts) {
        this.courts.addAll(courts);
    }

    public Optional<Court> findCourt(String courtName) {
        return courts.stream().filter(court -> Objects.equals(court.getName(), courtName)).findFirst();
    }
}
