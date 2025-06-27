package english.chatbot.application.port.in;

import english.chatbot.application.domain.entity.User;

public interface FindUserUseCase {

    User execute(String name);
}
