package authweb.service;

import authweb.entity.Role;
import authweb.entity.User;
import authweb.payload.CreateUserRequest;
import authweb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WebUserServiceTest {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    private final Map<String, User> users = new HashMap<>();

    @BeforeEach
    public void init() {
        userRepository = mock(UserRepository.class);
        when(userRepository.save(any(User.class))).then(
                i -> users.put(((User) i.getArgument(0)).getEmail(), i.getArgument(0))
        );
        when(userRepository.findByEmail(anyString())).thenAnswer(
                i -> users.get(i.getArgument(0))
        );
        when(userRepository.existsByEmail(anyString())).thenAnswer(
                i -> users.get(i.getArgument(0)) != null
        );
    }

    @Test
    public void createUser(){
        WebUserService webUserService = new WebUserService(userRepository, passwordEncoder);
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@test.ru");
        request.setFirstName("test");
        request.setLastName("testovich");

        webUserService.createUser(request, Role.ROLE_MANAGER);
        User user = userRepository.findByEmail("test@test.ru");
        assertNotNull(user);
        assertEquals("test", user.getFirstName());
        assertEquals("testovich", user.getLastName());
        assertNotNull(user.getPassword());
        assertArrayEquals(List.of(Role.ROLE_MANAGER).toArray(), user.getRoles().toArray());
    }

    @Test
    public void createUserException(){
        WebUserService webUserService = new WebUserService(userRepository, passwordEncoder);
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@test.ru");
        request.setFirstName("test");
        request.setLastName("testovich");
        webUserService.createUser(request, Role.ROLE_MANAGER);
        assertEquals(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE), webUserService.createUser(request, Role.ROLE_MANAGER));
    }
}