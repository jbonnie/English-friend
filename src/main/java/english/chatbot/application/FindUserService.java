package english.chatbot.application;

import english.chatbot.application.entity.User;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.out.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

    private final FindUserPort findUserPort;

    @Override
    public User execute(String name) {
        return findUserPort.byName(name).orElse(null);
    }
}
