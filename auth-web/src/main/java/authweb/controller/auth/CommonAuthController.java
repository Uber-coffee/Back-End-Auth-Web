package authweb.controller.auth;

import authweb.config.swagger2.SwaggerMethodToDocument;
import authweb.exception.TokenException;
import authweb.exception.UserNotFoundException;
import authweb.exception.WrongAuthServiceException;
import authweb.service.auth.CommonAuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class CommonAuthController {

    private final CommonAuthService commonAuthService;

    public CommonAuthController(CommonAuthService commonAuthService) {
        this.commonAuthService = commonAuthService;
    }

    @SwaggerMethodToDocument
    @PostMapping(value = "/refresh")
    @ApiOperation(value = "Refresh your token")
    public void refresh(HttpServletRequest httpServletRequest,
                        HttpServletResponse httpServletResponse)
            throws TokenException, UserNotFoundException, WrongAuthServiceException {
        commonAuthService.refresh(httpServletRequest, httpServletResponse);
    }

    @SwaggerMethodToDocument
    @PostMapping(value = "/logout")
    @ApiOperation(value = "Register someone's log-out")
    public void logout(HttpServletRequest httpServletRequest) throws TokenException {
        commonAuthService.logout(httpServletRequest);
    }

    @SwaggerMethodToDocument
    @RequestMapping(value = "/validate")
    @ApiOperation(value = "Validate your token")
    public void validateToken(HttpServletRequest httpServletRequest) throws TokenException{
    }
}
