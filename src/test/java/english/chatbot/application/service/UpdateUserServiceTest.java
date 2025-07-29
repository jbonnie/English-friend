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

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

    @Mock
    FindUserPort findUserPort;
    @InjectMocks
    UpdateUserService updateUserService;

    static Stream<Arguments> provideArgs() {
        return Stream.of(
                Arguments.of(new User("Bonnie", "easy"), true, false),
                Arguments.of(new User("Steve", "normal"), false, true),
                Arguments.of(new User("John", "hard"), false, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    @DisplayName("등록되어있는 유저의 이름을 변경한다. 존재하지 않는 유저이거나 중복된 이름의 다른 유저가 있을 경우 예외가 발생한다.")
    void updateName(User user, boolean isPresent, boolean isNamePresent) {
        // given
        Long id = 1L;
        String name = "updated name";
        if(isPresent) {
            when(findUserPort.byId(anyLong())).thenReturn(Optional.of(user));
        } else {
            when(findUserPort.byId(anyLong())).thenReturn(Optional.empty());
        }

        if(isPresent && isNamePresent) {
            when(findUserPort.byName(name)).thenReturn(Optional.of(user));
        } else if(isPresent && !isNamePresent) {
            when(findUserPort.byName(name)).thenReturn(Optional.empty());
        }

        // when & then
        // 1. 이미 등록된 유저 & 중복 이름의 유저가 없을 경우 > 정상 업데이트
        if(isPresent && !isNamePresent) {
            User updatedUser = updateUserService.updateName(id, name);
            assertThat(updatedUser).isNotNull();
            assertEquals(name, updatedUser.getName());
            verify(findUserPort, times(1)).byId(id);
            verify(findUserPort, times(1)).byName(name);
        }
        // 2. 이미 등록된 유저 & 중복 이름의 유저가 있을 경우 > 예외 발생
        else if(isPresent & isNamePresent) {
            Exception e = assertThrows(IllegalArgumentException.class, () -> {
               updateUserService.updateName(id, name);
            });
            assertEquals("이미 존재하는 이름입니다. 다른 이름으로 시도해주세요.", e.getMessage());
            verify(findUserPort, times(1)).byId(id);
            verify(findUserPort, times(1)).byName(name);
        }
        // 3. 등록되지 않은 유저 > 예외 발생
        else {
            Exception e = assertThrows(NoSuchElementException.class, () -> {
                updateUserService.updateName(id, name);
            });
            assertEquals("등록되지 않은 유저입니다. 회원가입 후 이용해주세요.", e.getMessage());
            verify(findUserPort, times(1)).byId(id);
            verify(findUserPort, never()).byName(name);
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    @DisplayName("등록되어있는 유저의 공부 난이도를 변경한다. 존재하지 않는 유저일 경우 예외가 발생한다.")
    void updateDifficulty(User user, boolean isPresent, boolean isNamePresent) {
        // given
        Long id = 1L;
        String difficulty = "hard";

        if(isPresent) {
            when(findUserPort.byId(anyLong())).thenReturn(Optional.of(user));
        } else {
            when(findUserPort.byId(anyLong())).thenReturn(Optional.empty());
        }

        // when & then
        if(isPresent) {
            User updatedUser = updateUserService.updateDifficulty(id, difficulty);
            assertThat(updatedUser).isNotNull();
            assertEquals(difficulty.toUpperCase(), updatedUser.getDifficulty().name());
            verify(findUserPort, times(1)).byId(id);
        } else {
            Exception e = assertThrows(NoSuchElementException.class, () -> {
                updateUserService.updateDifficulty(id, difficulty);
            });
            assertEquals("등록되지 않은 유저입니다. 회원가입 후 이용해주세요.", e.getMessage());
            verify(findUserPort, times(1)).byId(id);
        }
    }

    @ParameterizedTest
    @MethodSource("provideArgs")
    @DisplayName("등록되어있는 유저의 최근 공부 기록을 업데이트한다.")
    void updateStudy(User user, boolean isPresent, boolean isNamePresent) {
        // given
        int originalScore = user.getScore();

        // when
        updateUserService.updateStudy(user);

        // then
        assertEquals(originalScore + 1, user.getScore());
        assertThat(user.getLastStudy()).isNotNull();
    }
}