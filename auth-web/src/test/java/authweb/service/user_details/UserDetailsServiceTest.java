package authweb.service.user_details;

import authweb.entity.Role;
import authweb.entity.User;
import authweb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserDetailsServiceTest {

    private UserRepository userRepository;

    private final Map<String, User> users = new HashMap<>();

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        when(userRepository.findByEmail(anyString())).thenAnswer(
                i -> users.get(i.getArgument(0))
        );
        User user = new User(
                (long) 1,
                "test",
                "testovich",
                "test@test.ru",
                "88005553535",
                "password",
                List.of(Role.ROLE_MANAGER)
        );
        users.put(user.getEmail(), user);
    }

    @Test
    void loadUserByUsername() {
        UserDetailsService userDetailsService = new UserDetailsService(userRepository);
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@test.ru");
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), "1");
        assertEquals(userDetails.getPassword(), "password");
        assertArrayEquals(userDetails.getAuthorities().toArray(), List.of(Role.ROLE_MANAGER).toArray());
    }
}