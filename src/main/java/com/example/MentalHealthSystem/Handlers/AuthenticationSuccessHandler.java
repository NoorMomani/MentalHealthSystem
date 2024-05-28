package com.example.MentalHealthSystem.Handlers;

import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.repository.DoctorRepository;
import com.example.MentalHealthSystem.service.ValidatorService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final ValidatorService validatorService;

    public AuthenticationSuccessHandler(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        boolean isDoctor = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> UserRoles.DOCTOR.equals(UserRoles.getEnumByValue(grantedAuthority.getAuthority())));
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> UserRoles.ADMIN.equals(UserRoles.getEnumByValue(grantedAuthority.getAuthority())));
        if (isAdmin){
            setDefaultTargetUrl("/admins/dashboard");
        } else if(isDoctor){
            UserDetails doctor = (UserDetails) authentication.getPrincipal();
            if (validatorService.isActiveDoctor(doctor.getUsername())) {
                setDefaultTargetUrl("/doctors/dashboard");
            } else {
                setDefaultTargetUrl("/login_failed");
            }
        }

        else
//            setDefaultTargetUrl("/patients/dashboard");
        setDefaultTargetUrl("/homepage");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
