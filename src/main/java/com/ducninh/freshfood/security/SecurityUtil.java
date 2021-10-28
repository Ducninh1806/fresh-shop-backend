package com.ducninh.freshfood.security;

import com.ducninh.freshfood.model.User;
import com.ducninh.freshfood.security.service.UserPrinciple;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class SecurityUtil {

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user.
     */
    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof User) {
                        User springSecurityUser = (User) authentication.getPrincipal();
                        return springSecurityUser.getEmail();
                    } else if (authentication.getPrincipal() instanceof String) {
                        return (String) authentication.getPrincipal();
                    }
                    return null;
                });
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static Optional<String> getCurrentUserJWT() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise.
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
                    return authorities.stream()
                            .noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ANONYMOUS"));
                })
                .orElse(false);
    }

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
            return a.getId();
        }
        return null;
    }

    /**
     * Get the login of the current userId.
     *
     * @return the login of the current userId.
     */
    public static String getCurrentUserEmailLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            UserPrinciple a = (UserPrinciple) authentication.getPrincipal();
            return a.getEmail();
        }
        return null;
    }

    /**
     * If the current user has a specific authority (security role).
     * <p>
     * The name of this method comes from the {@code isUserInRole()} method in the Servlet API.
     *
     * @param authority the authority to check.
     * @return true if the current user has the authority, false otherwise.
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .map(authentication -> {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.addAll(authentication.getAuthorities());
                    return authorities.stream()
                            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority));
                })
                .orElse(false);
    }
}
