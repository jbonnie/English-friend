package english.chatbot.application.port.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.application.entity.User;

public interface RegisterUserUseCase {

    User execute(SignUpRequestDto requestDto);
}
