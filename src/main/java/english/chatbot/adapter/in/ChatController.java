package english.chatbot.adapter.in;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.ChatUseCase;
import english.chatbot.application.port.in.FindUserUseCase;
import english.chatbot.application.port.in.GeneratePromptUsecase;
import english.chatbot.application.util.DateTimeUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final FindUserUseCase findUserUseCase;
    private final GeneratePromptUsecase generatePromptUsecase;
    private final ChatUseCase chatUseCase;

    @PostMapping("/chat")
    public String chat(@RequestBody String message,
                       @CookieValue(name = "userId", required = true) Cookie cookie) {
        // 쿠키에서 userId 가져오기
        Long id = Long.parseLong(cookie.getValue());
        User user = findUserUseCase.byId(id);
        // 프롬프트 생성
        Prompt prompt = generatePromptUsecase.execute(user, message);
        String response = chatUseCase.chat(prompt);
        // 유저 score 업데이트
        user.updateStudy();
        return response;
    }
}
