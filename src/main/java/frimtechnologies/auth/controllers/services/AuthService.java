package frimtechnologies.auth.controllers.services;

import frimtechnologies.auth.controllers.services.payloads.LoginRequest;
import frimtechnologies.auth.controllers.services.payloads.LoginResponse;
import frimtechnologies.auth.domains.User;
import frimtechnologies.auth.validator.SessionValidator;
import frimtechnologies.configurations.security.JwtUtils;
import frimtechnologies.fileresources.controllers.services.FileService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@ApplicationScoped
public class AuthService {

    @Inject
    JwtUtils jwtUtils;

    @Inject
    SessionValidator validator;

    @Inject
    FileService fileService;

    @Inject
    UserService userService;

    public LoginResponse login(LoginRequest request) {

        User user = User.login(request.email, request.password);

        String jwt = jwtUtils.generateJwtToken(user.email);

        LoginResponse loginInfo = new LoginResponse();
        loginInfo.user = user;
        loginInfo.jwt = jwt;

        return loginInfo;
    }


}
