package english.chatbot.application;

import english.chatbot.application.port.in.GeneratePromptUsecase;
import jakarta.transaction.Transactional;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GeneratePromptService implements GeneratePromptUsecase {

    @Override
    public Prompt execute(String message) {
        List<Message> messages = new ArrayList<>();
        // 첫 대화 시작일 경우
        SystemMessage systemMessage = new SystemMessage("너는 아주 유능한 영어 회화 선생님이야. 상대방의 메세지에 대해 영어로 응답하면서 영어 공부를 도와줘.");
        UserMessage userMessage = new UserMessage(message);

        messages.add(systemMessage);
        messages.add(userMessage);

        return new Prompt(messages);
    }
}
