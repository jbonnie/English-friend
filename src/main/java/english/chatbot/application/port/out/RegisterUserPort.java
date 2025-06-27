package english.chatbot.application.port.out;

import english.chatbot.application.entity.User;

public interface RegisterUserPort {
    User save(User user);
}
