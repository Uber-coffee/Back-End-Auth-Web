package authweb.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class WebUsersListRequest {

    @NotBlank
    private List<String> roles;
}
