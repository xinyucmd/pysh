
package com.jiangchuanbanking.system.security;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.jiangchuanbanking.base.service.IBaseService;
import com.jiangchuanbanking.system.domain.Role;

/**
 * Security metadata source
 */
public class SecurityMetadataSource implements
        FilterInvocationSecurityMetadataSource {

    private IBaseService<Role> roleService;
    public static Map<String, Collection<ConfigAttribute>> permissionMap = null;

    public SecurityMetadataSource(IBaseService<Role> roleService) {
        this.roleService = roleService;
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    public boolean supports(Class<?> clazz) {
        return true;
    }

    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        return null;
    }

    /**
     * @return the roleService
     */
    public IBaseService<Role> getRoleService() {
        return roleService;
    }

    /**
     * @param roleService
     *            the roleService to set
     */
    public void setRoleService(IBaseService<Role> roleService) {
        this.roleService = roleService;
    }

}
