package english.chatbot.application.service;

import english.chatbot.application.domain.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeneratePromptServiceTest {

    @Mock
    ChatMemory chatMemory;
    @InjectMocks
    GeneratePromptService generatePromptService;

    static Stream<Arguments> provideArgs() {
        User user = new User("Bonnie", "easy");
        String conversationId = "conversationId";
        String message = "user's question";
        List<Message> emptyExistingMessages = Arrays.asList();
        List<Message> nonEmptyExistMessages = Arrays.asList(
                new SystemMessage("system message"),
                new UserMessage("user message"),
                new AssistantMessage("assistant message")
        );

        return Stream.of(
            Arguments.of(conversationId, user, message, emptyExistingMessages),
            Arguments.of(conversationId, user, message, nonEmptyExistMessages)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    @DisplayName("사용자의 이전 채팅 기록을 불러온 뒤 ai 질의를 위한 프롬프트를 구성한다.")
    void execute(String conversationId, User user, String message, List<Message> existingMessages) {
        // given
        when(chatMemory.get(conversationId)).thenReturn(existingMessages);
        doNothing().when(chatMemory).add(anyString(), anyList());

        // when
        Prompt actual = generatePromptService.execute(conversationId, user, message);

        // then
        assertThat(actual).isNotNull();
        assertThat(actual.getInstructions()).hasSize(existingMessages.isEmpty() ? 2 : existingMessages.size() + 1);
        verify(chatMemory, times(1)).get(conversationId);
        verify(chatMemory, times(1)).add(anyString(), anyList());
    }
}