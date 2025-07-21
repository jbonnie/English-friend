package english.chatbot.application.port.in;

import org.springframework.ai.chat.prompt.Prompt;

public interface GeneratePromptUsecase {

    Prompt execute(String message);
}
