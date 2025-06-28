package english.chatbot.application.domain.entity;

import english.chatbot.application.domain.Difficulty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "member")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 1, max = 20)
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ColumnDefault("0")
    private int score;

    private LocalDateTime lastStudy;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    public User(String name, String difficulty) {
        this.name = name;
        this.difficulty = Difficulty.from(difficulty);
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateDifficulty(String difficulty) {
        this.difficulty = Difficulty.from(difficulty);
    }

    public void updateStudy() {
        this.score++;
        this.lastStudy = LocalDateTime.now();
    }
}
