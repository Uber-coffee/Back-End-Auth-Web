package authweb.security.filter;

import authweb.exception.TokenException;
import authweb.exception.UserNotFoundException;
import authweb.exception.WrongAuthServiceException;
import authweb.security.token.AccessTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DefaultTokenAuthFilter extends OncePerRequestFilter {

    private final AccessTokenProvider accessTokenProvider;

    public DefaultTokenAuthFilter(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException{
        try {
            accessTokenProvider.validateToken(httpServletRequest);
            SecurityContextHolder.getContext().setAuthentication(accessTokenProvider.getAuthentication(httpServletRequest));
        } catch (TokenException e) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired JWT.");
            return;
        } catch (UserNotFoundException e) {
            SecurityContextHolder.clearContext();
            httpServletResponse.sendError(HttpStatus.I_AM_A_TEAPOT.value(), "Everything is ok, but user doesn't exists.");
            return;
        } catch (WrongAuthServiceException e){
            httpServletResponse.sendError(HttpStatus.CONFLICT.value(), "Why are you still here??");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
