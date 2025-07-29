package english.chatbot.adapter.out;

import english.chatbot.application.domain.entity.User;
import english.chatbot.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RegisterUserAdapterTest {

    @Autowired
    UserRepository userRepository;
    RegisterUserAdapter registerUserAdapter;

    @BeforeEach
    void setUp() {
        registerUserAdapter = new RegisterUserAdapter(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    static Stream<Arguments> provideUser() {
        User user1 = new User("Bonnie", "easy");
        User user2 = new User("not exist user", "normal");

        return Stream.of(
                Arguments.of(user1),
                Arguments.of(user2)
        );
    }

    @ParameterizedTest
    @MethodSource("provideUser")
    @DisplayName("유저를 정상적으로 등록한다.")
    void save(User user) {
        // given
        // when
        User savedUser = registerUserAdapter.save(user);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).extracting(User::getName, User::getDifficulty)
                .containsExactly(user.getName(), user.getDifficulty());
    }
}