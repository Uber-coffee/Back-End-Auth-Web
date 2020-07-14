package authweb.payload;

import authweb.entity.Role;
import authweb.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private DateTime registrationDate;

    private List<Role> roles;

    public UserDTO getDTOFromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setRegistrationDate(getRegistrationDate());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}

