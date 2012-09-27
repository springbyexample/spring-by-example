package ua.com.springbyexample.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.springbyexample.dao.UserDetailsDao;
import ua.com.springbyexample.domain.User;
import ua.com.springbyexample.domain.UserDetailsAdapter;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: orezchykov
 * Date: 25.09.12
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class SbeUserDetailsService implements UserDetailsService {

    @Resource
    private UserDetailsDao userDetailsDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        User user  = userDetailsDao.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Email is not found in the database");
        }
        return new UserDetailsAdapter(user);
    }
}
