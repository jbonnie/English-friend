package english.chatbot.application;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.SendMessageToLlmUseCase;
import english.chatbot.application.port.out.FindUserPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class SendMessageToLlmService implements SendMessageToLlmUseCase {

    private final FindUserPort findUserPort;
    private final ChatClient chatClient;

    @Override
    public String execute(Long userId, String userMsg) {
        // 유저 정보 조회
        User user = findUserPort.byId(userId)
                .orElseThrow(() -> new NoSuchElementException("등록되지 않은 유저입니다. 회원가입 후 이용해주세요."));

        // 유저가 선택한 난이도에 맞춰서 프롬프트 작성
        SystemMessage systemMessage = new SystemMessage("You are my English conversation teacher. You need to help me with " + user.getDifficulty() + " level English.");
        UserMessage userMessage = new UserMessage(userMsg);
        List<Message> messages = List.of(systemMessage, userMessage);
        Prompt prompt = new Prompt(messages);

        // 응답 받아오기
        String response = chatClient.call(prompt).getResult().getOutput().getContent();

        // 유저의 점수 + 1, 마지막 공부 시간 업데이트
        user.updateStudy();
        return response;
    }
}
