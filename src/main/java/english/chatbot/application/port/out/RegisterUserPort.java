package english.chatbot.application.port.out;

import english.chatbot.application.domain.entity.User;

public interface RegisterUserPort {
    User save(User user);
}
