package com.example.MentalHealthSystem.Handlers;

import com.example.MentalHealthSystem.constants.UserRoles;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;

@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.error("hi There");
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority ->{
                    log.error(grantedAuthority.getAuthority() + " Hi Noor");
                    return UserRoles.DOCTOR.name().equalsIgnoreCase(grantedAuthority.getAuthority());
                } );
        if(isDoctor)
            setDefaultTargetUrl("/doctor/home");
        else
            setDefaultTargetUrl("/patient/home");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
