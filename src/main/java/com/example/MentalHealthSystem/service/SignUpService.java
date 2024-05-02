package com.example.MentalHealthSystem.service;

import com.example.MentalHealthSystem.Database.Patient;
import com.example.MentalHealthSystem.Database.UserLoginDetails;
import com.example.MentalHealthSystem.constants.Language;
import com.example.MentalHealthSystem.constants.UserRoles;
import com.example.MentalHealthSystem.repository.PatientRepository;
import com.example.MentalHealthSystem.repository.UserLoginDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Log4j2
@Service
public class SignUpService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private UserLoginDetailsRepository userLoginDetailsRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean signUp(String email, String password) {
        try {
            log.error(password);
            log.error(email);
            UserLoginDetails userLoginDetails = new UserLoginDetails();
            userLoginDetails.setEmail(email);
            userLoginDetails.setPassword(passwordEncoder.encode(password));
            userLoginDetails.setRole(UserRoles.PATIENT);
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(1999,1,30);
//            List<Language> languages = new ArrayList<>();
//            languages.add(Language.ARABIC);
//            languages.add(Language.ENGLISH);
//            // Create and save the user
//            Patient patient = Patient.builder()
//                    .email(email)
//                    .age(15)
//                    .address("jordan")
//                    .name("suhib")
//                    .dateOfBirth(calendar.getTime())
//                    .language(languages)
//                    .nationality("jo")
//                    .password(passwordEncoder.encode(password))
//                    .gender("male")
//                    .build();
//            log.warn("hi there");
//            patientRepository.save(patient); // Save using the instance, not directly UserRepository
//

            userLoginDetailsRepository.save(userLoginDetails);
            // Signup successful
            return true;
        } catch (Exception e){
            log.error("exception ",e);
            return false;
        }

    }


}
