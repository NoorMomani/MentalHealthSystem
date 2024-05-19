package com.example.MentalHealthSystem.Database;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;



@Data
@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Table(name = "Patient",uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Patient extends User{
    public Patient(){ super();}
}
