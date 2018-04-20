package org.edu.timelycourse.mc.biz.security;

import org.edu.timelycourse.mc.beans.model.UserModel;
import org.edu.timelycourse.mc.biz.repository.UserRepository;
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

    @Override
    public UserDetails loadUserByUsername (String mobile) throws UsernameNotFoundException
    {
        UserModel user = userRepository.getByUserPhone(mobile);
        if (user == null)
        {
            throw new UsernameNotFoundException(String.format("No user found with mobile '%s'.", mobile));
        }

        return JwtUserFactory.create(user);
    }
}
