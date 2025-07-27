package english.chatbot;

import org.springframework.ai.model.chat.memory.repository.jdbc.autoconfigure.JdbcChatMemoryRepositoryAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication(exclude = {JdbcChatMemoryRepositoryAutoConfiguration.class})
public class ChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatbotApplication.class, args);
	}

}
