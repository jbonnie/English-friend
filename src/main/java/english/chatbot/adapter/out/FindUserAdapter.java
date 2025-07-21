package english.chatbot.adapter.out;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.out.FindUserPort;
import english.chatbot.infrastructure.repository.UserRepository;
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

    @Override
    public Optional<User> byId(Long id) {
        return userRepository.findById(id);
    }
}
