/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frimtechnologies.auth.validator;

import frimtechnologies.auth.domains.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;


@ApplicationScoped
public class SessionValidator {

    public static _SessionStatus validate(String useremail) {

        _SessionStatus sessionstatus = new _SessionStatus();

        if (useremail.equals("anonymous")) {
            sessionstatus.status = false;
            sessionstatus.response = "Anonymous user!";

            return sessionstatus;
        }

        Optional<User> user = User.findByEmail(useremail);
        if (user.isEmpty()) {
            sessionstatus.status = false;
            sessionstatus.response = "Invalid Credentials!";

            return sessionstatus;
        }
        sessionstatus.user = user.get();

        sessionstatus.status = true;

        return sessionstatus;
    }
}
