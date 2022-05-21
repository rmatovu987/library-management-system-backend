package frimtechnologies.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;

@Entity
public class Student extends PanacheEntity {

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Email
    @Column(nullable = false)
    public String email;

    @Column(nullable = true)
    public String phoneNumber;

    @Column(nullable = false)
    public String school;

    public Student() {
    }

    public Student(String firstName, String lastName, String email, String phoneNumber, String school) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.school = school;
    }
}
