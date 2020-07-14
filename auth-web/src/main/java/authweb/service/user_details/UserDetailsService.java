package authweb.service.user_details;

import authweb.entity.User;
import authweb.exception.UserNotFoundException;
import authweb.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Long id;

        try {
            id = Long.parseLong(email);
        }catch (NumberFormatException e){
            throw new UsernameNotFoundException("Nice ID, Bro");
        }

        final User user = userRepository.findById(id).orElseThrow(()->new UsernameNotFoundException("Bad ID, Bro"));
        if(user == null) throw new UsernameNotFoundException("");
        return org.springframework.security.core.userdetails.User.builder()
                .username(Long.toString(user.getId()))
                .password(user.getPassword())
                .authorities(user.getRoles())
                .disabled(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .accountExpired(false)
                .build();
    }
}
