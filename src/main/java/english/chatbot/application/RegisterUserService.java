package english.chatbot.application;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.adapter.in.dto.SignUpResponseDto;
import english.chatbot.application.entity.User;
import english.chatbot.application.port.in.RegisterUserUseCase;
import english.chatbot.application.port.out.RegisterUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final RegisterUserPort registerUserPort;

    @Override
    public SignUpResponseDto execute(SignUpRequestDto requestDto) {
        User savedUser = registerUserPort.save(new User(requestDto.getName(), requestDto.getDifficulty()));
        return SignUpResponseDto.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .difficulty(savedUser.getDifficulty())
                .build();
    }
}
