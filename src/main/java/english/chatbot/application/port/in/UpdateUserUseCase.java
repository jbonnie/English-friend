package english.chatbot.application.port.in;

import english.chatbot.application.domain.entity.User;

public interface UpdateUserUseCase {

    User updateName(Long id, String name);
    User updateDifficulty(Long id, String difficulty);
}
