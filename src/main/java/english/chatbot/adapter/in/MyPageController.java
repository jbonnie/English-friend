package english.chatbot.adapter.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.adapter.in.dto.SignUpResponseDto;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
        boolean isAlreadyExist = findUserUseCase.execute(requestDto.getName());
        if(isAlreadyExist) {
            return "이미 존재하는 이름입니다. 다른 이름으로 시도해주세요.";
        }

        // 유저 신규 등록
        SignUpResponseDto responseDto = registerUserUseCase.execute(requestDto);
        return "정상적으로 등록되었습니다. 학습을 시작해보세요!";
    }
}
