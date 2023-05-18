package prada.teno.domain.review.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prada.teno.domain.crawling.domain.Center;
import prada.teno.domain.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Table(name="Review")
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 300)
    private String content;

    private int grade;

    private LocalDateTime uploadTime;

    private LocalDateTime updateTime;

    @ManyToOne
    @JoinColumn(name="CENTER_ID")
    private Center center;

    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User user;

    @Builder
    public Review(long id, String content, int grade, Center center, User user, LocalDateTime uploadTime, LocalDateTime updateTime) {
        this.id = id;
        this.content = content;
        this.grade = grade;
        this.center = center;
        this.user = user;
        this.uploadTime = uploadTime;
        this.updateTime = updateTime;
    }
}
