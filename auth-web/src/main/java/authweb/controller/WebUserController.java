package authweb.controller;

import authweb.config.swagger2.SwaggerMethodToDocument;
import authweb.entity.Role;
import authweb.payload.*;
import authweb.service.WebUserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.List;

@RestController
@RequestMapping(value = "/w/user")
public class WebUserController {

    private final WebUserService webUserService;

    public WebUserController(WebUserService webUserService) {
        this.webUserService = webUserService;
    }

    @SwaggerMethodToDocument
    @PostMapping(value = "/manager")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "To create a manager's account, provide an e-mail, first and last name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 403, message = "Such User already exists"),
    })
    public ResponseEntity<UserDTO> createManager(@Valid @RequestBody CreateUserRequest createUserRequest){
        return webUserService.createUser(createUserRequest, Role.ROLE_MANAGER);
    }

    @SwaggerMethodToDocument
    @PostMapping(value = "/seller")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @ApiOperation(value = "To create a seller's account, provide an e-mail, first and last name")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 406, message = "Such User already exists"),
    })
    public ResponseEntity<UserDTO> createSeller(@Valid @RequestBody CreateUserRequest createUserRequest){
        return webUserService.createUser(createUserRequest, Role.ROLE_SELLER);
    }

    @SwaggerMethodToDocument
    @PatchMapping(value = "/updateUser")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @ApiOperation(value = "To update user's account data, provide an e-mail, phone number first and last name")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "User created successfully"),
            @ApiResponse(code = 406, message = "Such User already exists"),
    })
    public ResponseEntity<HttpStatus> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest){
        return webUserService.updateUser(updateUserRequest);
    }

    @SwaggerMethodToDocument
    @DeleteMapping(value = "/deleteUser")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @ApiOperation(value = "To delete a user's account, provide an e-mail")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "User created successfully"),
            @ApiResponse(code = 406, message = "Such User already exists"),
    })
    public ResponseEntity<HttpStatus> deleteUser(@Valid @RequestParam @Email String email){
        return webUserService.deleteUser(email);
    }

    @SwaggerMethodToDocument
    @PostMapping(value = "/getUsers")
    @PreAuthorize(value = "hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @ApiOperation(value = "To get a list of users of given lists of Roles from a User")
    @ApiResponses(value = {
        @ApiResponse(code = 202, message = "Successful operation"),
        @ApiResponse(code = 403, message = "Wrong request format"),
    })
    public ResponseEntity<List<UserDTO>> getUsersByRole(@Valid @RequestBody WebUsersListRequest webUsersListRequest){
        return webUserService.getUsers(webUsersListRequest);
    }
}