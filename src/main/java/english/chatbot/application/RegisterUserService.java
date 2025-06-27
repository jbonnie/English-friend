package english.chatbot.application;

import english.chatbot.application.entity.User;
import english.chatbot.application.port.in.RegisterUserUseCase;
import english.chatbot.application.port.out.RegisterUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterUserService implements RegisterUserUseCase {

    private final RegisterUserPort registerUserPort;

    @Override
    public User execute(String name, String difficulty) {
        return registerUserPort.save(new User(name, difficulty));
    }
}
