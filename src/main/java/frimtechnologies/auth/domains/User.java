package frimtechnologies.auth.domains;

import frimtechnologies.fileresources.domain.FileResource;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.ws.rs.WebApplicationException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.Optional;

import org.wildfly.security.password.Password;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

import io.quarkus.elytron.security.common.BcryptUtil;

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

    public LocalDateTime entryTime = LocalDateTime.now();

    @OneToOne
    @JoinColumn(nullable=true)
    public FileResource photo;

    public User() {
    }

    public User(String firstName, String lastName, String password, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public static User login(String username, String password) {

        Optional<User> user = User.find("email = ?1", username).singleResultOptional();

        if (user.isEmpty()) {
            throw new WebApplicationException("Invalid credentials! Access denied!", 401);
        }

        if (verifyPassword(password, user.get().password)) {
            return user.get();
        } else {
            throw new WebApplicationException("Invalid credentials! Access denied!", 401);
        }
    }

    public static Boolean verifyPassword(String originalPwd, String encryptedPwd) {
        Logger logger = LoggerFactory.getLogger(User.class);

        try {
            // convert encrypted password string to a password key
            Password rawPassword = ModularCrypt.decode(encryptedPwd);

            try {
                // create the password factory based on the bcrypt algorithm
                PasswordFactory factory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT);

                try {

                    // create encrypted password based on stored string
                    BCryptPassword restored = (BCryptPassword) factory.translate(rawPassword);

                    // verify restored password against original
                    return factory.verify(restored, originalPwd.toCharArray());

                } catch (InvalidKeyException e) {
                    logger.error("Invalid password key: {}", e.getMessage());

                }

            } catch (NoSuchAlgorithmException e) {
                logger.error("Invalid Algorithm: {}", e.getMessage());

            }

        } catch (InvalidKeySpecException e) {
            logger.error("Invalid key: {}", e.getMessage());

        }

        return false;

    }

    public static void updatePassword(User user, String password) {
        user.password = BcryptUtil.bcryptHash(password);
    }

    public static Optional<User> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}
