package english.chatbot.application.port.out;

import english.chatbot.application.domain.entity.User;

import java.util.Optional;

public interface FindUserPort {

    Optional<User> byName(String name);
    Optional<User> byId(Long id);
}
