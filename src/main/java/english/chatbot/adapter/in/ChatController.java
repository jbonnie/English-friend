package english.chatbot.adapter.in;

import english.chatbot.application.port.in.SendMessageToLlmUseCase;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SendMessageToLlmUseCase sendMessageToLlmUseCase;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody String message,
                                  @CookieValue(name = "userId", required = true) Cookie cookie) {

        // 쿠키에서 userId 가져오기
        Long id = Long.parseLong(cookie.getValue());
        String response = sendMessageToLlmUseCase.execute(id, message);
        return ResponseEntity.ok(response);
    }
}
