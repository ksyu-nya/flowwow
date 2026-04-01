package com.flower.service;



import com.flower.model.entity.User;
import com.flower.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("admin");
        testUser.setPassword("encodedPassword");
        testUser.setActive(true);
    }

    // ==================== ТЕСТ-КЕЙСЫ ====================

    @Test
    void loadUserByUsername_shouldReturnUserDetails_whenUserExists() {
        // given
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(testUser));

        // when
        UserDetails result = userService.loadUserByUsername("admin");

        // then
        assertThat(result.getUsername()).isEqualTo("admin");
        assertThat(result.getPassword()).isEqualTo("encodedPassword");
        verify(userRepository).findByUsername("admin");
    }

    @Test
    void loadUserByUsername_shouldThrowException_whenUserNotFound() {
        // given
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.loadUserByUsername("unknown"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Админ не найден");
        verify(userRepository).findByUsername("unknown");
    }

    @Test
    void findByUsername_shouldReturnUser_whenExists() {
        // given
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(testUser));

        // when
        User result = userService.findByUsername("admin");

        // then
        assertThat(result.getUsername()).isEqualTo("admin");
        verify(userRepository).findByUsername("admin");
    }

    @Test
    void findByUsername_shouldThrowException_whenUserNotFound() {
        // given
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.findByUsername("unknown"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Админ не найден");
        verify(userRepository).findByUsername("unknown");
    }
}
