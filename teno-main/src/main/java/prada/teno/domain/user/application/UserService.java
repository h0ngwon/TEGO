package prada.teno.domain.user.application;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prada.teno.domain.user.dto.RequestChangePassword;
import prada.teno.domain.user.dto.RequestCreateUser;
import prada.teno.domain.user.dto.RequestDeleteUser;
import prada.teno.domain.user.dto.RequestLoginUser;
import prada.teno.domain.user.dto.ResponseLoginUser;
import prada.teno.domain.user.dto.ResponseUser;
import prada.teno.domain.user.domain.User;
import prada.teno.domain.user.repository.UserRepository;
import prada.teno.domain.review.dto.ResponseReview;
import prada.teno.global.exception.InvalidOldPasswordException;
import prada.teno.global.util.JwtUtil;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public ResponseLoginUser save(
            RequestCreateUser requestCreateUser
    ) {
        User user = requestCreateUser.toEntity();
        User saveUser;

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("already exist email");
        } else if (userRepository.findByNickName(user.getNickName()).isPresent()) {
            throw new IllegalArgumentException("already exist nickname");
        } else {

            String encodedPassword = passwordEncoder.encode(user.getUserPassword());
            user.setUserPassword(encodedPassword);
            saveUser = userRepository.save(user);

            return ResponseLoginUser.builder()
                    .responseUser(ResponseUser.fromEntity(saveUser))
                    .jwtToken(jwtUtil.generateToken(saveUser.getEmail()))
                    .build();
        }
    }

    public ResponseLoginUser login(
            RequestLoginUser requestLoginUser
    ) {
        User user = userRepository.findByEmail(requestLoginUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("no user"));

        if (passwordEncoder.matches(requestLoginUser.getPassword(), user.getUserPassword())) {
            return ResponseLoginUser.builder()
                    .responseUser(ResponseUser.fromEntity(user))
                    .jwtToken(jwtUtil.generateToken(user.getEmail()))
                    .build();
        } else {
            throw new BadCredentialsException("not same password");
        }
    }

    public boolean isUniqueEmail(
            String email
    ) {
        if (userRepository.findByEmail(email).isPresent())
            return false;
        else
            return true;
    }

    public boolean isUniqueNickname(
            String nickname
    ) {
        if (userRepository.findByNickName(nickname).isPresent())
            return false;
        else
            return true;
    }

    @Transactional
    public ResponseUser updatePassword(
            long id, RequestChangePassword requestChangePassword
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("no user"));

        if (passwordEncoder.matches(requestChangePassword.getPassword(), user.getUserPassword())) {
            user.setUserPassword(passwordEncoder.encode(requestChangePassword.getUpdatePassword()));
        } else {
            throw new InvalidOldPasswordException("기존 비밀번호가 일치하지 않아 변경에 실피했습니다");
        }

        return ResponseUser.fromEntity(user);
    }

    @Transactional
    public ResponseUser updateNickname(
            long id, String nickname
    ) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("no user"));

        user.setNickName(nickname);

        return ResponseUser.fromEntity(user);
    }

    @Transactional
    public void deleteUser(
            long id, RequestDeleteUser requestDeleteUser
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("no user"));

        if (passwordEncoder.matches(requestDeleteUser.getPassword(), user.getUserPassword())) {
            user.delete();
        } else {
            throw new InvalidOldPasswordException("비밀번호가 일치하지 않아 삭제에 실패했습니다");
        }
    }

    @Transactional(readOnly = true)
    public List<ResponseReview> findUserReviews(
            long id
    ) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("no user"));

        return user.getReviews()
                .stream()
                .map(ResponseReview::fromEntity)
                .collect(Collectors.toList());
    }
}
