package english.chatbot.application.service;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.UpdateUserUseCase;
import english.chatbot.application.port.out.FindUserPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {

    private final FindUserPort findUserPort;

    @Override
    public User updateName(Long id, String name) {
        // 기존 유저 정보 조회
        User user = findUserPort.byId(id)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 유저입니다. 회원가입 후 이용해주세요."));

        // 이미 존재하는 이름인지 확인
        Optional<User> userWithNewName = findUserPort.byName(name);
        if(userWithNewName.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이름입니다. 다른 이름으로 시도해주세요.");
        }

        user.updateName(name);
        return user;
    }

    @Override
    public User updateDifficulty(Long id, String difficulty) {
        // 기존 유저 정보 조회
        User user = findUserPort.byId(id)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 유저입니다. 회원가입 후 이용해주세요."));

        user.updateDifficulty(difficulty);
        return user;
    }
}
