package english.chatbot.application.port.in;

import english.chatbot.application.domain.entity.User;

public interface RegisterUserUseCase {

    User execute(String name, String difficulty);
}
