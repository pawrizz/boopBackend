package boop.user.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_profiles")
public class DoctorProfile {

    @Id
    private Long id; // This will match the User ID exactly

    @OneToOne
    @MapsId // This tells JPA to use the User's ID as this table's Primary Key
    @JoinColumn(name = "user_id")
    private User user;

    private String medicalLicenseNumber;
    private String specialization;
    private String clinicAddress;

    // address, age, etc.
}
