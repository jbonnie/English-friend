package english.chatbot.application.service;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.out.FindUserPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserServiceTest {

    @Mock
    FindUserPort findUserPort;
    @InjectMocks
    FindUserService findUserService;

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
    @DisplayName("id에 해당하는 유저를 조회한다. 존재하지 않을 경우 예외가 발생한다.")
    void byId(User user, boolean isPresent) {
        // given
        Optional<User> userResult;
        if(isPresent) {
            userResult = Optional.of(user);
        } else {
            userResult = Optional.empty();
        }

        when(findUserPort.byId(anyLong())).thenReturn(userResult);
        Long id = 1L;

        // when & then
        if(isPresent) {
            User actual = findUserService.byId(id);
            assertEquals(user, actual);
        } else {
            Exception e = assertThrows(IllegalArgumentException.class, () -> findUserService.byId(id));
            assertEquals("등록되지 않은 유저입니다. 회원가입 후 이용해주세요.", e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideUser")
    @DisplayName("이름에 해당하는 유저를 조회한다. 존재하지 않을 경우 예외가 발생한다.")
    void byName(User user, boolean isPresent) {
        // given
        Optional<User> userResult;
        if(isPresent) {
            userResult = Optional.of(user);
        } else {
            userResult = Optional.empty();
        }

        when(findUserPort.byName(anyString())).thenReturn(userResult);
        String name = "user name";

        // when
        User actual = findUserService.byName(name);

        // then
        if(isPresent) {
            assertThat(actual).isNotNull();
            assertEquals(user, actual);
        } else {
            assertThat(actual).isNull();
        }
    }
}