package com.ducninh.freshfood.security;

import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.security.service.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtil {

    /**
     * Get the login of the current userId.
     *
     * @return the login of the current userId.
     */
    public static Long getCurrentUserIdLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            UserPrinciple a = (UserPrinciple) authentication.getPrincipal();
//            User springSecurityUser = (User) authentication.getPrincipal();
            return a.getId();
//            return null;
        }
        return null;
    }
}
