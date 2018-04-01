package org.edu.energycourse.mc.web.security;

import org.edu.energycourse.mc.common.constants.Constants;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

/**
 * Created by x36zhao on 2017/3/19.
 */
public class RefererRedirectionAuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler
{
    public RefererRedirectionAuthenticationSuccessHandler()
    {
        super();
        setTargetUrlParameter(Constants.REDIRECT_URL_PARAMETER);
        setUseReferer(true);
    }
}
