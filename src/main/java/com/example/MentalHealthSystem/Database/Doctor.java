package com.example.MentalHealthSystem.Database;

import com.example.MentalHealthSystem.constants.DoctorStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Blob;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
public class Doctor extends User{
    public Doctor(){super();}
    @Column(name = "yearsOfExperience")
    int yearsOfExperience;

    @Column(name = "jobTitle")
    private String jobTitle;

    @Column(name = "cvFileName")
    private String cvFileName; // Store file name instead

    @Column(name = "cvContent")
    private Blob cvContent; // Store file content as byte array

    @Column(name = "cvContentType")
    private String cvContentType; // Store content type

    @Column(name = "identityLicenseFileName")
    private String identityLicenseFileName; // Store file name instead

    @Column(name = "identityLicenseContent")
    private Blob identityLicenseContent; // Store file content as byte array

    @Column(name = "identityLicenseContentType")
    private String identityLicenseContentType; // Store content type

    @Column(name = "sessionPrice")
    private int sessionPrice;

    @Column(name = "aboutTheDoctor")
    private String aboutTheDoctor;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    Set<Appointment> appointments;

    @Column(name = "educations")
    private Set<Education> educations;

    @Column(name = "status")
    private DoctorStatus status;
}
