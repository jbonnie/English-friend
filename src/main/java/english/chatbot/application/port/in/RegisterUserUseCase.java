package english.chatbot.application.port.in;

import english.chatbot.application.entity.User;

public interface RegisterUserUseCase {

    User execute(String name, int difficulty);
}
