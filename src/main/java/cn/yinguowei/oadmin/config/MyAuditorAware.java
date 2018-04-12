package cn.yinguowei.oadmin.config;

import org.springframework.data.domain.AuditorAware;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author yinguowei 2017/7/27.
 */
@Component
public class MyAuditorAware implements AuditorAware<String> {
    private static final Optional<String> SYSTEM_ACCOUNT = Optional.of("SYSTEM");

    @Override
    public Optional<String> getCurrentAuditor() {
//        SecurityContext context = SecurityContextHolder.getContext();
//        if (context == null || context.getAuthentication() == null || context.getAuthentication().getPrincipal() == null) {
            return SYSTEM_ACCOUNT;
//        }
//        Object principal = context.getAuthentication().getPrincipal();
//        if (principal.getClass().isAssignableFrom(User.class)) {
//            return Optional.of(((User) principal).getUsername());
//        }
//        throw new RuntimeException("getCurrentAuditor: Auditor must not be null!");
    }
}
