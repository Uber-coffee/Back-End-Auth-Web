package authweb.payload;

import authweb.entity.Role;
import authweb.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String registrationDate;

    private List<Role> roles;

    public UserDTO getDTOFromUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRegistrationDate(user.getRegistrationDate().toString());
        userDTO.setRoles(user.getRoles());
        return userDTO;
    }
}

