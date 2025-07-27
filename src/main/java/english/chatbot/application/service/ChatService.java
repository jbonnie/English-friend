package english.chatbot.application.service;

import english.chatbot.application.port.in.ChatUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService implements ChatUseCase {

    private final ChatModel chatModel;
    private final ChatMemory chatMemory;

    @Override
    public String execute(String conversationId, Prompt prompt) {
        ChatResponse chatResponse = chatModel.call(prompt);
        String response = chatResponse.getResult().getOutput().getText();

        // 채팅 응답 저장
        assert response != null;
        List<Message> newMessages = new ArrayList<>();
        newMessages.add(new AssistantMessage(response));
        chatMemory.add(conversationId, newMessages);

        return response;
    }
}
