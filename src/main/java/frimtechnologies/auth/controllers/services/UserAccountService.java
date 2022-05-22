package frimtechnologies.auth.controllers.services;

import frimtechnologies.auth.controllers.services.payloads.PasswordUpdateRequest;
import frimtechnologies.auth.domains.User;
import frimtechnologies.fileresources.controllers.services.FileRequest;
import frimtechnologies.fileresources.controllers.services.FileService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.util.Optional;

@ApplicationScoped
public class UserAccountService {

    @Inject
    FileService fileService;

    public String updatePasword(String username, PasswordUpdateRequest request) {
        Optional<User> entity = User.findByEmail(username);
        if (entity.isEmpty()) {
            throw new WebApplicationException("Invalid user!", 404);
        }

        if (request.newPassword.length() > 3
                && request.newPassword.equals(request.confirmPassword)
                && !request.newPassword.equals(request.oldPassword)) {

            if (User.verifyPassword(request.oldPassword, entity.get().password)) {
                User.updatePassword(entity.get(), request.newPassword);

            } else {
                return "Invalid password!";
            }

        } else {
            return "Invalid Password!";
        }

        return "Password updated successfully!";
    }

    public String updateProfilePic(String username, FileRequest file) {

        Optional<User> entity = User.findByEmail(username);
        if (entity.isEmpty()) {
            throw new WebApplicationException("Invalid user!", 404);
        }

        if (entity.get().photo == null) {
            entity.get().photo = fileService.create(file);
        } else {
            entity.get().photo = fileService.update(entity.get().photo.id, file);
        }

        entity.get().persist();

        return "User profile picture updated successfully!";
    }

}
