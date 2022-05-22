package frimtechnologies.auth.controllers.endpoints;

import java.security.Principal;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import frimtechnologies.auth.controllers.services.UserService;
import frimtechnologies.auth.controllers.services.payloads.UserRequest;
import frimtechnologies.auth.domains.User;
import frimtechnologies.auth.validator.SessionValidator;
import frimtechnologies.auth.validator._SessionStatus;
import frimtechnologies.configurations.handlers.ResponseMessage;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("user")
@Produces("application/json")
@Consumes("application/json")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Authorization - User", description = "User controller")
public class UserController {

    @Inject
    UserService service;

    @POST
    @Transactional
    @Operation(summary = "Create User", description = "This will create a user")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    public Response create(UserRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String user = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(user);
        if (session.status) {
            return Response.ok(new ResponseMessage("User created successfully.", service.create(session.user, request)))
                    .status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @GET
    @Transactional
    @Operation(summary = "Get users", description = "This will get a list of users")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = User.class)))
    public Response getList(@Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String user = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(user);
        if (session.status) {
            return Response.ok(new ResponseMessage("Users fetched successfully.", service.get())).status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @GET
    @Path("{id}")
    @Transactional
    @Operation(summary = "Get details", description = "This will return user details.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    public Response getDetails(@PathParam("id") Long id, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String user = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(user);
        if (session.status) {

            return Response.ok(new ResponseMessage("User details fetched successfully.", service.getSingle(id))).status(200)
                    .build();
        }

        throw new WebApplicationException(session.response, 401);

    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update user", description = "This will update user details.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    public Response update(@PathParam("id") Long id, UserRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String user = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(user);
        if (session.status) {

            return Response.ok(
                            new ResponseMessage("User updated successfully.", service.updateUser(session.user, id, request)))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete user", description = "This will delete a user.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = User.class)))
    public Response delete(@PathParam("id") Long id, UserRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String user = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(user);
        if (session.status) {

            return Response.ok(new ResponseMessage("User deleted successfully.", service.delete(id)))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

}
