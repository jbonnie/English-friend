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

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class FindUserAdapterTest {
    
    @Autowired
    UserRepository userRepository;
    FindUserAdapter findUserAdapter;

    @BeforeEach
    void setUp() {
        findUserAdapter = new FindUserAdapter(userRepository);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }
    
    static Stream<Arguments> provideUser() {
        User user1 = new User("Bonnie", "easy");
        User user2 = new User("not exist user", "normal");
        
        return Stream.of(
            Arguments.of(user1, true),
            Arguments.of(user2, false)    
        );
    }

    @ParameterizedTest
    @MethodSource("provideUser")
    @DisplayName("이름에 해당하는 유저를 조회한다. 존재하는 유저가 없을 경우 빈 Optional 객체가 반환된다.")
    void byName(User user, boolean isPresent) {
        // given
        User savedUser = null;
        if(isPresent) {
            savedUser = userRepository.save(user);
        }
        
        // when
        Optional<User> actual = findUserAdapter.byName(user.getName());
        
        // then
        if(isPresent) {
            assertThat(actual).isPresent();
            assertEquals(savedUser, actual.get());
        } else {
            assertThat(actual).isEmpty();
        }
    }

    @ParameterizedTest
    @MethodSource("provideUser")
    @DisplayName("id에 해당하는 유저를 조회한다. 존재하는 유저가 없을 경우 빈 Optional 객체가 반환된다.")
    void byId(User user, boolean isPresent) {
        // given
        User savedUser = null;
        Long id = 999L;
        if(isPresent) {
            savedUser = userRepository.save(user);
            id = savedUser.getId();
        }

        // when
        Optional<User> actual = findUserAdapter.byId(id);

        // then
        if(isPresent) {
            assertThat(actual).isPresent();
            assertEquals(savedUser, actual.get());
        } else {
            assertThat(actual).isEmpty();
        }
    }
}