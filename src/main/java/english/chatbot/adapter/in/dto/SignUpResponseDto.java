package english.chatbot.adapter.in.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDto {

    private Long id;
    private String name;
    private int difficulty;
}
