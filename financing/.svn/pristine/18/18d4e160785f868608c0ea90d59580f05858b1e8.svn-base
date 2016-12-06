
package com.jiangchuanbanking.system.security;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.jiangchuanbanking.system.domain.Role;
import com.jiangchuanbanking.system.domain.User;
import com.jiangchuanbanking.system.service.IUserService;

/**
 * User details service implementation
 */
public class UserDetailsServiceImpl implements UserDetailsService {
    private IUserService userService;

    public UserDetails loadUserByUsername(String username) {

        UserDetailsImpl userDetails = new UserDetailsImpl();

        User user = null;
        try {
            user = userService.findByName(username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error found in getting user。");
        }
        if (user == null) {
            throw new UsernameNotFoundException(
                    "Hasn't found user information.");
        }
        userDetails.setUsername(user.getName());
        String password = user.getPassword();
        if (password == null) {
            password = "";
        }
        userDetails.setPassword(user.getPassword());
        userDetails.setCredentialsNonExpired(true);
        userDetails.setAccountNonExpired(true);

        userDetails.setAccountNonLocked(true);
        userDetails.setEnabled(true);
        Set<Role> roles = user.getRoles();

        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                    role.getName());
            authorities.add(grantedAuthority);
            try {
                UserUtil.setAccessValue(role, user);
            } catch (Exception e) {
                throw new RuntimeException("Failed to set the Access value");
            }
        }
        userDetails.setAuthorities(authorities);
        userDetails.setUser(user);
        return userDetails;
    }

    /**
     * @return the userService
     */
    public IUserService getUserService() {
        return userService;
    }

    /**
     * @param userService
     *            the userService to set
     */
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
