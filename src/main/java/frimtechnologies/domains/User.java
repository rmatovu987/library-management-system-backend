package frimtechnologies.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import java.util.Optional;

@Entity
public class User extends PanacheEntity {

    @Column(nullable = false)
    public String firstName;

    @Column(nullable = false)
    public String lastName;

    @Column(nullable = false)
    public String password;

    @Email
    @Column(nullable = false)
    public String email;

    @Column(nullable = true)
    public String phoneNumber;

    public User() {
    }

    public User(String firstName, String lastName, String password, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static User login(String email, String password){
        Optional<User> user = User.find("email", email).firstResultOptional();
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
    }
}
