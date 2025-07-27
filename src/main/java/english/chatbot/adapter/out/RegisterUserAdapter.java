package english.chatbot.adapter.out;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.out.RegisterUserPort;
import english.chatbot.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegisterUserAdapter implements RegisterUserPort {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
