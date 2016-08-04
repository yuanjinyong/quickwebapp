/**
 * 
 */
package com.quickwebapp.usm.sys.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

/**
 * @author 袁进勇
 *
 */
@Service("customInvocationSecurityMetadataSourceService")
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    private static final Logger LOG = LoggerFactory.getLogger(CustomInvocationSecurityMetadataSourceService.class);

    @Autowired
    private SecurityCacheManager securityCacheManager;

    // 根据request，找到相关的权限配置。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        Collection<ConfigAttribute> authorityList = securityCacheManager.getRequestAuthorities(fi.getRequest());
        LOG.debug("需要权限：{}", authorityList);
        return authorityList;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

}
