package english.chatbot.application.port.in;

import org.springframework.ai.chat.prompt.Prompt;

public interface ChatUseCase {

    String execute(String conversationId, Prompt prompt);
}
