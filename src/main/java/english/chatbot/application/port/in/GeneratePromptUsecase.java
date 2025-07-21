package english.chatbot.application.port.in;

import english.chatbot.application.domain.entity.User;
import org.springframework.ai.chat.prompt.Prompt;

public interface GeneratePromptUsecase {

    Prompt execute(User user, String message);
}
