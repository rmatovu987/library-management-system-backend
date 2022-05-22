package frimtechnologies.library.controllers.endpoints;

import frimtechnologies.auth.validator.SessionValidator;
import frimtechnologies.auth.validator._SessionStatus;
import frimtechnologies.configurations.handlers.ResponseMessage;
import frimtechnologies.library.controllers.services.StudentService;
import frimtechnologies.library.controllers.services.payloads.StudentRequest;
import frimtechnologies.library.domains.Student;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

@Path("student")
@Produces("application/json")
@Consumes("application/json")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Library - Student", description = "Student controller")
public class StudentController {

    @Inject
    StudentService service;

    @POST
    @Transactional
    @Operation(summary = "Create Student", description = "This will create a student")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Student.class)))
    public Response create(StudentRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {
            return Response.ok(new ResponseMessage("Student created successfully.", service.create(request)))
                    .status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @GET
    @Transactional
    @Operation(summary = "Get students", description = "This will get a list of students")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Student.class)))
    public Response getList(@Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {
            return Response.ok(new ResponseMessage("Students fetched successfully.", service.get())).status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update student", description = "This will update student details.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Student.class)))
    public Response update(@PathParam("id") Long id, StudentRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {

            return Response.ok(
                            new ResponseMessage("Student updated successfully.", service.update(id, request)))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete student", description = "This will delete a student.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Student.class)))
    public Response delete(@PathParam("id") Long id, StudentRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {
            service.delete(id);
            return Response.ok(new ResponseMessage("Student deleted successfully."))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

}
