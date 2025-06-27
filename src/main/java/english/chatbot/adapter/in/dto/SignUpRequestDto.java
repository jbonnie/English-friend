package english.chatbot.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String difficulty;
}
