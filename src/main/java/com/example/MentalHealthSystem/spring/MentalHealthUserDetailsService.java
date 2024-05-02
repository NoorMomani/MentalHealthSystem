package com.example.MentalHealthSystem.spring;

import com.example.MentalHealthSystem.Database.UserLoginDetails;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.repository.UserLoginDetailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class MentalHealthUserDetailsService implements UserDetailsService {
    @Autowired
    UserLoginDetailsRepository userLoginDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.error(username);
        Optional<UserLoginDetails> optionalUser = userLoginDetailsRepository.findByEmail(username);
        if (optionalUser.isPresent()){
            UserLoginDetails user = optionalUser.get();
            return User.builder().username(user.getEmail())
                    .password(user.getPassword())
                    .roles(getRole(user.getRole()))
                    .build();

        } else{
            log.error("couldn't find username");
            throw new UsernameNotFoundException(username);
        }
    }

    private String getRole(UserRoles userRoles){
        return Objects.requireNonNullElse(userRoles, UserRoles.GUST).name();
    }
}
