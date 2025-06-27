package english.chatbot.application.port.in;

import english.chatbot.application.entity.User;

public interface FindUserUseCase {

    User execute(String name);
}
