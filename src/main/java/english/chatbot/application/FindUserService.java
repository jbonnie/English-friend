package english.chatbot.application;

import english.chatbot.application.entity.User;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.out.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

    private final FindUserPort findUserPort;

    @Override
    public boolean execute(String name) {
        Optional<User> user = findUserPort.byName(name);
        return user.isPresent();
    }
}
