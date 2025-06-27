package english.chatbot.adapter.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.adapter.in.dto.SignUpResponseDto;
import english.chatbot.application.port.in.RegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final RegisterUseCase registerUseCase;

    // 첫 회원 등록
    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequestDto signUpRequestDto) {
        // TODO 이미 존재하는 유저 네임인지 확인


        // 유저 신규 등록
        SignUpResponseDto responseDto = registerUseCase.execute(signUpRequestDto);
        return "정상적으로 등록되었습니다. 학습을 시작해보세요!";
    }
}
