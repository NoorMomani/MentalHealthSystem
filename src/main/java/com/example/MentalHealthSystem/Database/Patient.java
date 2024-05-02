package com.example.MentalHealthSystem.Database;

import com.example.MentalHealthSystem.constants.Language;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


@Data
@Entity
@Builder
@EqualsAndHashCode(callSuper=false)
@Table(name = "Patient",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Patient{

    @Id
    String email;

    @Column(name = "name")
    String name;

    @Column(name = "dateOfBirth")
    Date dateOfBirth;

    @Column(name = "nationality")
    String nationality;

    @Column(name = "phoneNumber")
    String phoneNumber;

    @Column(name = "address")
    String address;

    @Column(name = "gender")
    String gender;

    @Column(name = "age")
    int age;

    @Column(name = "language")
    List<Language> language;
}
