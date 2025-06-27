package english.chatbot.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;
    @ColumnDefault("EASY")
    private String difficulty;      // EASY / NORMAL / HARD
    @ColumnDefault("0")
    private int score;
    private LocalDateTime lastStudy;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(String name, String difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
