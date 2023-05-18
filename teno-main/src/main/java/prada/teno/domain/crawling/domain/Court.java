package prada.teno.domain.crawling.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.crawling.domain.Time;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name="court")
@NoArgsConstructor
@Entity
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="center_id")
    private Center center;

    @OneToMany(mappedBy = "court", cascade=CascadeType.ALL)
    private List<Time> times;

    @Builder
    public Court(Center center, String name, List<Time> times) {
        this.center = center;
        this.name = name;
        this.times = times;
    }
}
