package english.chatbot.application.service;

import english.chatbot.application.domain.Difficulty;
import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.GeneratePromptUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneratePromptService implements GeneratePromptUseCase {

    private final ChatMemory chatMemory;

    @Override
    public Prompt execute(String conversationId, User user, String message) {

        // 이전 채팅 목록 조회 (1시간 이내 기준)
        List<Message> existingMessages = chatMemory.get(conversationId);
        List<Message> newMessages = new ArrayList<>();

        // 첫 대화 시작일 경우
        if(existingMessages.isEmpty()) {
            Difficulty difficulty = user.getDifficulty();
            String sysMessage = """
                너는 아주 유능한 영어 회화 선생님이야. 상대방의 메세지에 대해 영어로 응답하면서 영어 공부를 도와줘.
                상대방의 이름은 
                """
                    + user.getName()
                    + """
                이고, 상대방이 원하는 영어 회화의 난이도는 
                """
                    + difficulty.name()
                    + """
                 이야. 이 난이도에 맞춰서 대답을 해줘야해.
                """;
            newMessages.add(new SystemMessage(sysMessage));
        }

        newMessages.add(new UserMessage(message));

        // 새로운 메세지 기록 저장
        chatMemory.add(conversationId, newMessages);

        // 전체 메세지를 Prompt에 담아서 반환
        List<Message> allMessages = new ArrayList<>(existingMessages);
        allMessages.addAll(newMessages);

        return new Prompt(allMessages);
    }
}
