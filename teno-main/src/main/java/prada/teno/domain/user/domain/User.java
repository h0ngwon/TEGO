package prada.teno.domain.user.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.Where;
import prada.teno.domain.review.domain.Review;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Table(name="USER")
@Entity
@Where(clause = "deleted=false")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String nickName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviews;

    private Boolean deleted = false;

    @Builder
    public User(String email, String userPassword, String nickName) {

        this.userPassword = userPassword;
        this.nickName = nickName;
        this.email = email;
    }

    public void delete() {
        this.deleted = true;
    }
}