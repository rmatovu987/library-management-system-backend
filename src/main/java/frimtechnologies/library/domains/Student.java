package frimtechnologies.library.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Entity
public class Student extends PanacheEntity {

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(nullable = true)
    public String studentCode;

    @Email
    @Column(nullable = true)
    public String email;

    @Column(nullable = true)
    public String phoneNumber;

    @Column(nullable = false)
    public String school;

    public LocalDateTime entryTime = LocalDateTime.now();

    public Student() {
    }

    public Student(String firstName, String lastName,String studentCode, String email, String phoneNumber, String school) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentCode = studentCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.school = school;
    }
}
