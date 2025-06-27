package english.chatbot.adapter.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.adapter.in.dto.UpdateUserRequestDto;
import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.in.RegisterUserUseCase;
import english.chatbot.application.port.in.UpdateUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final FindUserUseCase findUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    // 첫 회원 등록
    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignUpRequestDto requestDto) {
        // 이미 존재하는 유저 네임인지 확인
        User user = findUserUseCase.execute(requestDto.getName());
        if(user != null) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다. 다른 이름으로 시도해주세요.");
        }

        // 유저 신규 등록
        User saveduser = registerUserUseCase.execute(requestDto.getName(), requestDto.getDifficulty());
        // TODO: JWT를 이용하여 유저 관리
        return "정상적으로 등록되었습니다. 학습을 시작해보세요!";
    }

    // 로그인 시도
    @PostMapping("/login")
    public String login(@RequestParam("name") String name) {
        User user = findUserUseCase.execute(name);
        if(user == null) {
            throw new IllegalArgumentException("등록되지 않은 유저입니다. 회원가입 후 이용해주세요.");
        }
        // TODO: JWT를 이용하여 유저 관리
        return "환영합니다, " + name + "님!";
    }

    // 이름 수정
    @PatchMapping("/mypage/update/name")
    public String updateName(@RequestBody UpdateUserRequestDto requestDto) {
        if(requestDto.getName() == null) {
            throw new IllegalArgumentException("수정하고자 하는 이름을 입력해주세요.");
        }

        // TODO: JWT 토큰으로 기존 유저 정보 가져오기
        Long id = 1L;
        return updateUserUseCase.updateName(id, requestDto.getName());
    }

    // 선택 난이도 수정
    @PatchMapping("/mypage/update/difficulty")
    public String updateDifficulty(@RequestBody UpdateUserRequestDto requestDto) {
        if(requestDto.getDifficulty() == null) {
            throw new IllegalArgumentException("수정하고자 하는 난이도를 선택해주세요.");
        }

        // TODO: JWT 토큰으로 기존 유저 정보 가져오기
        Long id = 1L;
        return updateUserUseCase.updateDifficulty(id, requestDto.getDifficulty());
    }

}
