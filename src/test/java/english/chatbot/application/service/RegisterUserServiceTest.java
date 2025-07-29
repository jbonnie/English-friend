package english.chatbot.application.service;

import english.chatbot.application.domain.entity.User;
import english.chatbot.application.port.out.RegisterUserPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterUserServiceTest {

    @Mock
    RegisterUserPort registerUserPort;
    @InjectMocks
    RegisterUserService registerUserService;

    static Stream<Arguments> provideArgs() {
        return Stream.of(
                Arguments.of(new User("Bonnie", "easy")),
                Arguments.of(new User("Steve", "normal")),
                Arguments.of(new User("John", "hard"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    @DisplayName("새로운 유저를 정상적으로 등록한다.")
    void execute(User user) {
        // given
        when(registerUserPort.save(any())).thenReturn(user);

        // when
        User savedUser = registerUserService.execute(user.getName(), user.getDifficulty().name());

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser).extracting(User::getName, User::getDifficulty)
                .containsExactly(user.getName(), user.getDifficulty());
    }
}