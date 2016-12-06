
package com.jiangchuanbanking.system.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * Url access decision manager
 */
public class UrlAccessDecisionManager implements AccessDecisionManager {

    public void decide(Authentication authentication, Object securityObject,
            Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        try {
            if (authentication.getPrincipal() instanceof String
                    && "anonymousUser".equals(authentication.getPrincipal())) {
                throw new AccessDeniedException(" no permission access！");
            }
            return;
        } catch (Exception e) {
            throw new AccessDeniedException("Role check fails.");
        }
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

}
