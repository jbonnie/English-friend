package english.chatbot.application.port.in;

public interface UpdateUserUseCase {

    String updateName(Long id, String name);
    String updateDifficulty(Long id, String difficulty);
}
