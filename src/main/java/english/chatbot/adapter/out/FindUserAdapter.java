package english.chatbot.adapter.out;

import english.chatbot.application.entity.User;
import english.chatbot.application.port.out.FindUserPort;
import english.chatbot.infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FindUserAdapter implements FindUserPort {

    private final UserRepository userRepository;

    @Override
    public Optional<User> byName(String name) {
        return userRepository.findByName(name);
    }
}
