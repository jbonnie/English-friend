package english.chatbot.adapter.in.dto;

import english.chatbot.application.domain.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String difficulty;
    private int score;
    private String lastStudy;
    private String createdAt;

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .difficulty(user.getDifficulty().name())
                .score(user.getScore())
                .lastStudy(dateFormatting(user.getLastStudy()))
                .createdAt(dateFormatting(user.getCreatedAt()))
                .build();
    }

    private static String dateFormatting(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return localDateTime.format(formatter);
    }
}
