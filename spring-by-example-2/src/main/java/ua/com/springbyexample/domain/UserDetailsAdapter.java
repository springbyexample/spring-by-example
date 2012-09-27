package ua.com.springbyexample.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: orezchykov
 * Date: 25.09.12
 * Time: 17:57
 * To change this template use File | Settings | File Templates.
 */
public class UserDetailsAdapter implements UserDetails {

    private static final GrantedAuthority ADMIN_AUTHORITY = new GrantedAuthorityImpl("admin");

    private User user;

    public UserDetailsAdapter(User user) {
        this.user  = user;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return Arrays.asList(ADMIN_AUTHORITY);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public static void main(String args[]) {
        PasswordEncoder encoder = new StandardPasswordEncoder("oleksiy.rezchykov@gmail.com");
        System.out.println(encoder.encode("qwer1234"));
    }
}
