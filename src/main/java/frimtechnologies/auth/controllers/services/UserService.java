package frimtechnologies.auth.controllers.services;

import frimtechnologies.auth.controllers.services.payloads.UserRequest;
import frimtechnologies.auth.domains.User;
import io.agroal.api.AgroalDataSource;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    AgroalDataSource dataSource;

    public User create(User user, UserRequest request){
        String password = BcryptUtil.bcryptHash(request.password);

        User newuser = new User();
        newuser.email = request.email;
        newuser.password = password;
        newuser.firstName = request.firstName;
        newuser.lastName = request.lastName;
        newuser.phoneNumber = request.phoneNumber;
        newuser.persist();

        return newuser;
    }

    public List<User> get() {

        return User.listAll();

    }

    public User getSingle(Long id) {

        return User.findById(id);

    }

    public User updateUser(User user, Long id, UserRequest request) {

        User entity = User.findById(id);

        if (entity == null) {
            throw new WebApplicationException("User with these details does not exist.", 404);
        }

        entity.firstName = request.firstName;
        entity.lastName = request.lastName;
        entity.phoneNumber = request.phoneNumber;
        entity.persist();

        return entity;
    }

    public User delete(Long id) {

        User entity = User.findById(id);

        if (entity == null) {
            throw new WebApplicationException("User with this Id does not exist.", 404);
        }

        entity.delete();

        return entity;
    }

}
