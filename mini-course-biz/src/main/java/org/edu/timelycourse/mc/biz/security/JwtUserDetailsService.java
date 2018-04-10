package org.edu.timelycourse.mc.biz.security;

import org.edu.timelycourse.mc.biz.model.UserModel;
import org.edu.timelycourse.mc.biz.repository.UserRepository;
import org.edu.timelycourse.mc.biz.security.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by x36zhao on 2018/4/9.
 */
@Service
public class JwtUserDetailsService implements UserDetailsService
{
    @Autowired
    private UserRepository userRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException
    {
        UserModel user = userRepository.getByUserPhone(username);
        if (user == null)
        {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }

        return JwtUserFactory.create(user);
    }
}
