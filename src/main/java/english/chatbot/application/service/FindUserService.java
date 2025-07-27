package english.chatbot.application.service;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.out.FindUserPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindUserService implements FindUserUseCase {

    private final FindUserPort findUserPort;

    @Override
    public User byId(Long id) {
        return findUserPort.byId(id)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저입니다. 회원가입 후 이용해주세요."));
    }

    @Override
    public User byName(String name) {
        return findUserPort.byName(name).orElse(null);
    }
}
