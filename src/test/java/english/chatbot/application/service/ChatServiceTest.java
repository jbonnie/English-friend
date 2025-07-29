package english.chatbot.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    @Mock
    ChatModel chatModel;
    @Mock
    ChatMemory chatMemory;
    @InjectMocks
    ChatService chatService;

    static Stream<Arguments> provideArgs() {
        ChatResponse chatResponse = mock(ChatResponse.class);
        Generation generation = mock(Generation.class);
        AssistantMessage assistantMessage = mock(AssistantMessage.class);
        String expectedResponse = "this is response from ai.";

        when(assistantMessage.getText()).thenReturn(expectedResponse);
        when(generation.getOutput()).thenReturn(assistantMessage);
        when(chatResponse.getResult()).thenReturn(generation);

        String conversationId = "conversationId";
        Prompt prompt = mock(Prompt.class);

        return Stream.of(
            Arguments.of(conversationId, prompt, chatResponse, expectedResponse)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    @DisplayName("사용자 질의에 대해 ai 응답을 받아오고, 응답도 대화기록으로 저장한다.")
    void execute(String conversationId, Prompt prompt, ChatResponse chatResponse, String expectedResponse) {
        // given
        when(chatModel.call(any(Prompt.class))).thenReturn(chatResponse);
        doNothing().when(chatMemory).add(anyString(), anyList());

        // when
        String actualResponse = chatService.execute(conversationId, prompt);

        // then
        assertEquals(expectedResponse, actualResponse);
        verify(chatModel, times(1)).call(any(Prompt.class));
        verify(chatMemory, times(1)).add(anyString(), anyList());
    }
}