package frimtechnologies.library.controllers.endpoints;

import frimtechnologies.auth.validator.SessionValidator;
import frimtechnologies.auth.validator._SessionStatus;
import frimtechnologies.configurations.handlers.ResponseMessage;
import frimtechnologies.library.controllers.services.IssueService;
import frimtechnologies.library.controllers.services.StudentService;
import frimtechnologies.library.controllers.services.payloads.IssueRequest;
import frimtechnologies.library.controllers.services.payloads.StudentRequest;
import frimtechnologies.library.domains.IssueBook;
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

@Path("issue")
@Produces("application/json")
@Consumes("application/json")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Library - Issue", description = "Issue controller")
public class IssueController {

    @Inject
    IssueService service;

    @POST
    @Transactional
    @Operation(summary = "Create book issue", description = "This will issue a abook")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = IssueBook.class)))
    public Response create(IssueRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {
            return Response.ok(new ResponseMessage("Book issued successfully.", service.issue(request)))
                    .status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @GET
    @Transactional
    @Operation(summary = "Get book issues", description = "This will get a list of book issues")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = IssueBook.class)))
    public Response getList(@Context SecurityContext ctx, @QueryParam("status") String status) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {
            return Response.ok(new ResponseMessage("Book issues fetched successfully.", service.get(status))).status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Return book", description = "This will return a book")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = IssueBook.class)))
    public Response update(@PathParam("id") Long id, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String student = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(student);
        if (session.status) {

            return Response.ok(
                            new ResponseMessage("Book returned successfully.", service.returnBook(id)))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

}
