package authweb.security.token;

import authweb.config.AppProperties;
import authweb.repository.InvalidTokenRepository;
import authweb.service.user_details.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenProvider extends TokenProvider {

    public static final String HEADER = "Refresh-token";

    private static final String TOKEN_TYPE = "Bearer";

    public RefreshTokenProvider(AppProperties appProperties,
                                UserDetailsService userDetailsService,
                                InvalidTokenRepository invalidTokenRepository) {
        super(HEADER,
                TOKEN_TYPE,
                invalidTokenRepository,
                appProperties.getRefreshToken(),
                userDetailsService);
    }
}
