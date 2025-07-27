package english.chatbot.adapter.in;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.ChatUseCase;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.in.GeneratePromptUseCase;
import english.chatbot.application.port.in.UpdateUserUseCase;
import english.chatbot.application.util.DateTimeUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final FindUserUseCase findUserUseCase;
    private final DateTimeUtil dateTimeUtil;
    private final GeneratePromptUseCase generatePromptUsecase;
    private final ChatUseCase chatUseCase;
    private final UpdateUserUseCase updateUserUseCase;

    @PostMapping("/chat")
    public String chat(@RequestBody String message,
                       @CookieValue(name = "userId", required = true) Cookie cookie) {
        Long id = Long.parseLong(cookie.getValue());
        User user = findUserUseCase.byId(id);
        String conversationId = id + "-" + dateTimeUtil.getCurrentTime();

        Prompt prompt = generatePromptUsecase.execute(conversationId, user, message);
        String response = chatUseCase.execute(conversationId, prompt);

        updateUserUseCase.updateStudy(user);

        return response;
    }
}
