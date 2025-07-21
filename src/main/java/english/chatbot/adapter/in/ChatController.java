package english.chatbot.adapter.in;

import english.chatbot.application.port.in.ChatUseCase;
import english.chatbot.application.port.in.GeneratePromptUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final GeneratePromptUsecase generatePromptUsecase;
    private final ChatUseCase chatUseCase;

    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        Prompt prompt = generatePromptUsecase.execute(message);
        return chatUseCase.chat(prompt);
    }
}
