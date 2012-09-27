package ua.com.springbyexample.dao;

import org.springframework.security.core.userdetails.UserDetails;
import ua.com.springbyexample.domain.User;

/**
 * Created with IntelliJ IDEA.
 * User: orezchykov
 * Date: 25.09.12
 * Time: 17:32
 * To change this template use File | Settings | File Templates.
 */
public interface UserDetailsDao  extends  GenericDao<User,Long>{

    User findByUsername(String email);
}
