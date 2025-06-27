package english.chatbot.application.port.in;

import english.chatbot.adapter.in.dto.SignUpRequestDto;
import english.chatbot.adapter.in.dto.SignUpResponseDto;

public interface RegisterUseCase {

    SignUpResponseDto execute(SignUpRequestDto requestDto);
}
