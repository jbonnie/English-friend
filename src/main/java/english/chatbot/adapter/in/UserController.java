package english.chatbot.adapter.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.adapter.in.dto.UpdateUserRequestDto;
import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.in.RegisterUserUseCase;
import english.chatbot.application.port.in.UpdateUserUseCase;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final FindUserUseCase findUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    // 첫 회원 등록
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequestDto requestDto,
                                 HttpServletResponse response) {
        // 이미 존재하는 유저 네임인지 확인
        User user = findUserUseCase.execute(requestDto.getName());
        if(user != null) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다. 다른 이름으로 시도해주세요.");
        }

        // 유저 신규 등록
        User savedUser = registerUserUseCase.execute(requestDto.getName(), requestDto.getDifficulty());

        // 쿠키에 userId 저장
        Cookie cookie = new Cookie("userId", ""+savedUser.getId());
        cookie.setPath("/");
        cookie.setMaxAge(60*60*5);        // 5시간 유지
        response.addCookie(cookie);
        return ResponseEntity.ok("정상적으로 등록되었습니다. 학습을 시작해보세요!");
    }

    // 로그인 시도
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("name") String name,
                        HttpServletResponse response) {
        User user = findUserUseCase.execute(name);
        if(user == null) {
            throw new IllegalArgumentException("등록되지 않은 유저입니다. 회원가입 후 이용해주세요.");
        }

        // 쿠키에 userId 저장
        Cookie cookie = new Cookie("userId", ""+user.getId());
        cookie.setPath("/");
        cookie.setMaxAge(60*60*5);        // 5시간 유지
        response.addCookie(cookie);
        return ResponseEntity.ok("환영합니다, " + name + "님!");
    }

    // 이름 수정
    @PatchMapping("/mypage/name")
    public ResponseEntity<?> updateName(@RequestBody UpdateUserRequestDto requestDto,
                             @CookieValue(name = "userId", required = false) Cookie cookie) {

        if(cookie == null) {
            throw new IllegalArgumentException("로그인 후 이용해주세요.");
        }

        if(requestDto.getName() == null) {
            throw new IllegalArgumentException("수정하고자 하는 이름을 입력해주세요.");
        }

        // 쿠키에서 userId 가져오기
        Long id = Long.parseLong(cookie.getValue());
        User user = updateUserUseCase.updateName(id, requestDto.getName());
        return ResponseEntity.ok(user);
    }

    // 선택 난이도 수정
    @PatchMapping("/mypage/difficulty")
    public ResponseEntity<?> updateDifficulty(@RequestBody UpdateUserRequestDto requestDto,
                                   @CookieValue(name = "userId", required = false) Cookie cookie) {

        if(cookie == null) {
            throw new IllegalArgumentException("로그인 후 이용해주세요.");
        }

        if(requestDto.getDifficulty() == null) {
            throw new IllegalArgumentException("수정하고자 하는 난이도를 선택해주세요.");
        }

        // 쿠키에서 userId 가져오기
        Long id = Long.parseLong(cookie.getValue());
        User user = updateUserUseCase.updateDifficulty(id, requestDto.getDifficulty());
        return ResponseEntity.ok(user);
    }

}
