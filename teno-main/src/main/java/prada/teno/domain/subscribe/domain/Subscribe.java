package prada.teno.domain.subscribe.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prada.teno.domain.crawling.domain.Time;
import prada.teno.domain.subscribe.util.SubscribeConverter;
import prada.teno.domain.subscribe.dto.SubscribeData;


import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Table(name="subscribe")
@NoArgsConstructor
@Entity
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="time_id")
    private Time time;

    private String userEmail;

    private boolean subscribe;

    private boolean deleted;

    private LocalDateTime startTime;

    @Column(columnDefinition = "varchar(255)")
    @Convert(converter = SubscribeConverter.class)
    private SubscribeData subscribeData;

    public Subscribe(Time time, String userEmail, boolean subscribe, LocalDateTime startTime, SubscribeData subscribeData) {
        this.time = time;
        this.userEmail = userEmail;
        this.subscribe = subscribe;
        this.deleted = false;
        this.startTime = startTime;
        this.subscribeData = subscribeData;
    }
}
