package authweb.service;

import authweb.entity.Role;
import authweb.entity.User;
import authweb.payload.CreateUserRequest;
import authweb.payload.UserDTO;
import authweb.payload.WebLoginRequest;
import authweb.payload.WebUsersListRequest;
import authweb.repository.UserRepository;
import authweb.util.PasswordUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class WebUserService {

    private static final ModelMapper mapper = new ModelMapper();

    private final Logger log = LoggerFactory.getLogger(WebUserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public WebUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<WebLoginRequest> createUser(CreateUserRequest createUserRequest, Role role){
        if(userRepository.existsByEmail(createUserRequest.getEmail())){
           return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        User user = mapper.map(createUserRequest, User.class);
        user.setRoles(List.of(role));
        final String password = PasswordUtil.generatePassword();

        user.setPassword(passwordEncoder.encode(password));
        // TODO send credentials to email address
        userRepository.save(user);
        log.debug("Create user with credentials: email({}), password({})", user.getEmail(), password);
        return new ResponseEntity<>(new WebLoginRequest(createUserRequest.getEmail(), password), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<UserDTO>> getUsers(WebUsersListRequest webUsersListRequest){

        List<Role> roles = new ArrayList<>();

        for (String s: webUsersListRequest.getRoles()) {
           roles.add(Role.getRole(s));
        }

        HashSet<User> users = userRepository.findAllByRolesIn(roles);

        if(users.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (User user: users) {
            user.setRegistrationDate(userRepository.getOne(user.getId()).getRegistrationDate());
        }

        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user:users) {
            userDTOs.add(new UserDTO().getDTOFromUser(user));
        }

        return new ResponseEntity<>(userDTOs, HttpStatus.ACCEPTED);
    }
}