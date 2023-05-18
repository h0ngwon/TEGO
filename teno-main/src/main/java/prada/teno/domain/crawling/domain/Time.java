package prada.teno.domain.crawling.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import prada.teno.domain.subscribe.domain.Subscribe;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name="time")
@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "time", cascade = CascadeType.ALL)
    private List<Subscribe> subscribeList;

    @ManyToOne
    @JoinColumn(name="court_id")
    private Court court;

    @Column(length = 1000)
    private String url;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private boolean reservable = true;

    @Builder
    public Time(long id, Court court, String url, LocalDateTime startTime, LocalDateTime endTime, boolean reservable) {
        this.id = id;
        this.court = court;
        this.url = url;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservable = reservable;
    }
}