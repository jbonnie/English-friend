package english.chatbot.adapter.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.application.entity.User;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final FindUserUseCase findUserUseCase;
    private final RegisterUserUseCase registerUserUseCase;

    // 첫 회원 등록
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequestDto requestDto) {
        // 이미 존재하는 유저 네임인지 확인
        User user = findUserUseCase.execute(requestDto.getName());
        if(user != null) {
            return "이미 존재하는 이름입니다. 다른 이름으로 시도해주세요.";
        }

        // 유저 신규 등록
        User saveduser = registerUserUseCase.execute(requestDto);
        // TODO: JWT를 이용하여 유저 관리
        return "정상적으로 등록되었습니다. 학습을 시작해보세요!";
    }

    // 로그인 시도
    @PostMapping("/login")
    public String login(@RequestParam("name") String name) {
        User user = findUserUseCase.execute(name);
        if(user == null) {
            return "등록되지 않은 유저입니다. 회원가입 후 이용해주세요.";
        }
        // TODO: JWT를 이용하여 유저 관리
        return "환영합니다, " + name + "님!";
    }
}
