package com.example.MentalHealthSystem.Database;

import com.example.MentalHealthSystem.constants.Language;
import com.example.MentalHealthSystem.constants.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

@Entity
@Data
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
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
