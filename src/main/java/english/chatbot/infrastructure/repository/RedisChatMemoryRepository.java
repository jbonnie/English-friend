package english.chatbot.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.messages.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RedisChatMemoryRepository implements ChatMemoryRepository {

    private final RedisTemplate<String, List<Message>> redisTemplate;

    @Override
    public List<String> findConversationIds() {
        return redisTemplate.keys("*").stream().toList();
    }

    @Override
    public List<Message> findByConversationId(String conversationId) {
        return redisTemplate.opsForValue().get(conversationId);
    }

    @Override
    public void saveAll(String conversationId, List<Message> messages) {
        redisTemplate.opsForValue().set(conversationId, messages);
    }

    @Override
    public void deleteByConversationId(String conversationId) {
        redisTemplate.delete(conversationId);
    }
}
