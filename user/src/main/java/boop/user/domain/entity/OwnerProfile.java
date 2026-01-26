package boop.user.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_profiles")
public class OwnerProfile {

    @Id
    private Long id; // This will match the User ID exactly

    @OneToOne
    @MapsId // This tells JPA to use the User's ID as this table's Primary Key
    @JoinColumn(name = "user_id")
    private User user;
    private String address;
    private Integer age;
    private String firstName;
    private String lastName;

}
