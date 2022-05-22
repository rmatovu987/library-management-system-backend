package frimtechnologies.library.controllers.services.payloads;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Email;

public class StudentRequest {

    @Schema(required = true)
    public String firstName;

    @Schema(required=true)
    public String lastName;

    public String studentCode;

    @Email
    public String email;

    public String phoneNumber;

    @Schema(required=true)
    public String school;
}
