package english.chatbot.application.port.in;

public interface SendMessageToLlmUseCase {

    String execute(Long userId, String userMsg);
}
