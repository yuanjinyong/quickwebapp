/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 袁进勇
 *
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private SecurityCacheManager securityCacheManager;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = securityCacheManager.getSecurityUser(username);
        if (securityUser == null) {
            LOG.error("账号[{}]不存在！", username);
            throw new UsernameNotFoundException("账号[" + username + "]不存在");
        }

        SecurityUser curUser = new SecurityUser(securityUser.getUsername(), securityUser.getPassword(),
                securityUser.getAuthorities());
        curUser.setUser(securityUser.getUser());
        // curUser.setMember(securityUser.getMember());
        return curUser;
    }
}
