package com.xyxy.platform.web.taglib;

import com.xyxy.platform.Constants;
import com.xyxy.platform.entity.User;
import com.xyxy.platform.service.account.ShiroDbRealm;
import com.xyxy.platform.spring.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.util.CollectionUtils;

/**
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
public class Functions {


    public static boolean in(Iterable iterable, Object element) {
        if (iterable == null) {
            return false;
        }
        return CollectionUtils.contains(iterable.iterator(), element);
    }

    public static String principal(Session session) {
        PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if (null != principalCollection) {
            ShiroDbRealm.ShiroUser shiroUser = (ShiroDbRealm.ShiroUser) principalCollection.getPrimaryPrincipal();
            return shiroUser.loginName;
        }
        return null;
    }

    public static boolean isForceLogout(Session session) {
        return session.getAttribute(Constants.SESSION_FORCE_LOGOUT_KEY) != null;
    }

    public static int status(User user) {
        if (null == user) return -1;
        if (user.getStatus() != User.Status.DISABLED) {
            for (Session session : getSessionDAO().getActiveSessions()) {
                //session 用户名
                String loginName = principal(session);
                if (user.getLoginName().equals(loginName)) {
                    return User.Status.ONLINE.getOrdinal();
                } else {
                    continue;
                }
            }
            return User.Status.OFFLINE.getOrdinal();
        }
        return user.getStatus().getOrdinal();
    }


    private static SessionDAO sessionDAO;

    public static SessionDAO getSessionDAO() {
        if (null == sessionDAO) {
            sessionDAO = SpringUtils.getBean(SessionDAO.class);
        }
        return sessionDAO;
    }


    /*private static OrganizationService organizationService;
    private static RoleService roleService;
    private static ResourceService resourceService;

    public static OrganizationService getOrganizationService() {
        if(organizationService == null) {
            organizationService = SpringUtils.getBean(OrganizationService.class);
        }
        return organizationService;
    }

    public static RoleService getRoleService() {
        if(roleService == null) {
            roleService = SpringUtils.getBean(RoleService.class);
        }
        return roleService;
    }

    public static ResourceService getResourceService() {
        if(resourceService == null) {
            resourceService = SpringUtils.getBean(ResourceService.class);
        }
        return resourceService;
    }*/
}

