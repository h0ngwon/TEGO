package prada.teno.domain.user.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import prada.teno.global.ResponseMessage;
import prada.teno.domain.user.dto.RequestChangePassword;
import prada.teno.domain.user.dto.RequestCreateUser;
import prada.teno.domain.user.dto.RequestDeleteUser;
import prada.teno.domain.user.dto.RequestLoginUser;
import prada.teno.domain.user.dto.ResponseLoginUser;
import prada.teno.domain.user.dto.ResponseUser;
import prada.teno.domain.user.application.UserService;
import prada.teno.domain.review.dto.ResponseReview;

import java.util.List;

@Tag(name = "User", description = "유저")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "user 회원가입", description = "user 등록을 위한 json을 받아 db에 저장", tags = "User")
    @PostMapping("/user/register")
    public ResponseEntity<ResponseLoginUser> save(
            @RequestBody RequestCreateUser requestCreateUser
    ) {
        return ResponseEntity.ok(userService.save(requestCreateUser));
    }

    @Operation(summary = "로그인", description = "이메일과 패스워드 입력을 받아 로그인 및 jwt 토큰 발행", tags = "User")
    @PostMapping("/login")
    public ResponseEntity<ResponseLoginUser> login(
            @RequestBody RequestLoginUser requestLoginUser
    ) {
        return ResponseEntity.ok(userService.login(requestLoginUser));
    }

    @Operation(summary = "이메일 중복 조회", description = "이메일을 입력받아 기존 user email과 중복되는지 확인", tags = "User")
    @GetMapping("/user/validate/email")
    public ResponseEntity<ResponseMessage> findDuplicateEmail(
            @Parameter(description = "유저 이메일", required = true, example = "bluesh55@naver.com") @RequestParam String email
    ) {
        if (userService.isUniqueEmail(email))
            return ResponseEntity.ok(
                    ResponseMessage.builder()
                            .message("possible email")
                            .build()
            );
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    ResponseMessage.builder()
                            .message("impossible email")
                            .build()
            );
    }

    @Operation(summary = "닉네임 중복 조회", description = "닉네임을 입력받아 기존 user 닉네임과 중복되는지 확인", tags = "User")
    @GetMapping("/user/validate/nickname")
    public ResponseEntity<ResponseMessage> findDuplicateNickName(
            @Parameter(description = "유저 닉네임", required = true, example = "오승환은야사시") @RequestParam String nickname
    ) {
        if (userService.isUniqueNickname(nickname))
            return ResponseEntity.ok(
                    ResponseMessage.builder()
                            .message("possible nickname")
                            .build()
            );
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ResponseMessage.builder()
                            .message("impossible nickname")
                            .build()
            );
    }

    @Operation(summary = "비밀번호 변경", description = "user pk와 변경 전 비밀번호, 변경할 비밀번호를 받아 비밀번호를 수정", tags = "User")
    @PutMapping("/user/{id}/password")
    public ResponseEntity<ResponseUser> updatePassword(
            @Parameter(description = "user pk", required = true, example = "1") @PathVariable long id,
            @RequestBody RequestChangePassword requestChangePassword
    ) {
        return ResponseEntity.ok(userService.updatePassword(id, requestChangePassword));
    }

    @Operation(summary = "닉네임 변경", description = "user pk와 변경할 닉네임을 받아 해당 유저의 닉네임을 변경", tags = "User")
    @PutMapping("/user/{id}/nickname")
    public ResponseEntity<ResponseUser> updateNickName(
            @Parameter(description = "user pk", required = true, example = "1") @PathVariable long id,
            @Parameter(description = "변경할 닉네임", required = true, example = "야니스의 코자") @RequestParam String nickname
    ) {
        return ResponseEntity.ok(userService.updateNickname(id, nickname));
    }

    @Operation(summary = "유저 삭제", description = "user pk와 비밀번호를 받아 해당 유저를 db에서 삭제(soft delete)", tags = "User")
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUser(
            @Parameter @PathVariable long id,
            @RequestBody RequestDeleteUser requestDeleteUser
    ) {
        userService.deleteUser(id, requestDeleteUser);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "유저의 리뷰들", description = "유저가 작성한 리뷰들을 확인", tags = "User")
    @GetMapping("/user/{id}/review")
    public ResponseEntity<List<ResponseReview>> findUserReview(
            @Parameter(description = "user pk", required = true, example = "1") @PathVariable long id
    ) {
        return ResponseEntity.ok(userService.findUserReviews(id));
    }
}
