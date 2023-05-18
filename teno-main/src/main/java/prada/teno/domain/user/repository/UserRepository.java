package prada.teno.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prada.teno.domain.user.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickName(String nickName);
}