package english.chatbot.application.service;

import english.chatbot.application.domain.Difficulty;
import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.in.GeneratePromptUsecase;
import english.chatbot.application.util.DateTimeUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GeneratePromptService implements GeneratePromptUsecase {

    private final ChatMemory chatMemory;
    private final DateTimeUtil dateTimeUtil;

    @Override
    public Prompt execute(User user, String message) {

        // 이전 채팅 목록 조회 (1시간 이내 기준)
        String conversationId = user.getId() + "-" + dateTimeUtil.getCurrentTime();
        List<Message> messages = chatMemory.get(conversationId);

        // 첫 대화 시작일 경우
        if(messages.isEmpty()) {
            Difficulty difficulty = user.getDifficulty();
            String sysMessage = """
                너는 아주 유능한 영어 회화 선생님이야. 상대방의 메세지에 대해 영어로 응답하면서 영어 공부를 도와줘.
                상대방이 원하는 영어 회화의 난이도는 
                """
                    + difficulty.name()
                    + """
                 이야. 이 난이도에 맞춰서 대답을 해줘야해.
                """;
            messages.add(new SystemMessage(sysMessage));
        }
        messages.add(new UserMessage(message));

        // chat memory 저장
        chatMemory.add(conversationId, messages);

        return new Prompt(messages);
    }
}
