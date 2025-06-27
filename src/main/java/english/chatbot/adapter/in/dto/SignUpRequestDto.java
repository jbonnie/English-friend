package english.chatbot.adapter.in.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequestDto {

    @NotBlank
    private String name;
    @NotNull
    private Integer difficulty;
}
