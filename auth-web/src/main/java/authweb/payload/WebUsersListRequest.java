package authweb.payload;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class WebUsersListRequest {

    @NotNull
    @Size(min = 1)
    private List<@NotBlank String> roles;
}
