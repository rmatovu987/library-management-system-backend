package frimtechnologies.library.controllers.endpoints;

import frimtechnologies.auth.validator.SessionValidator;
import frimtechnologies.auth.validator._SessionStatus;
import frimtechnologies.configurations.handlers.ResponseMessage;
import frimtechnologies.library.controllers.services.BookService;
import frimtechnologies.library.controllers.services.payloads.BookRequest;
import frimtechnologies.library.domains.Book;
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

@Path("book")
@Produces("application/json")
@Consumes("application/json")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Library - Book", description = "Book controller")
public class BookController {

    @Inject
    BookService service;

    @POST
    @Transactional
    @Operation(summary = "Create Book", description = "This will create a book")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Book.class)))
    public Response create(BookRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String book = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(book);
        if (session.status) {
            return Response.ok(new ResponseMessage("Book created successfully.", service.create(request)))
                    .status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @GET
    @Transactional
    @Operation(summary = "Get books", description = "This will get a list of books")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Book.class)))
    public Response getList(@Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String book = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(book);
        if (session.status) {
            return Response.ok(new ResponseMessage("Books fetched successfully.", service.get())).status(200).build();
        }
        throw new WebApplicationException(session.response, 401);
    }

    @PUT
    @Path("{id}")
    @Transactional
    @Operation(summary = "Update book", description = "This will update book details.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Book.class)))
    public Response update(@PathParam("id") Long id, BookRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String book = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(book);
        if (session.status) {

            return Response.ok(
                            new ResponseMessage("Book updated successfully.", service.update(id, request)))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    @Operation(summary = "Delete book", description = "This will delete a book.")
    @APIResponse(description = "Success", responseCode = "200", content = @Content(schema = @Schema(implementation = Book.class)))
    public Response delete(@PathParam("id") Long id, BookRequest request, @Context SecurityContext ctx) {
        Principal caller = ctx.getUserPrincipal();
        String book = caller == null ? "anonymous" : caller.getName();

        _SessionStatus session = SessionValidator.validate(book);
        if (session.status) {
            service.delete(id);
            return Response.ok(new ResponseMessage("Book deleted successfully."))
                    .status(200).build();
        }

        throw new WebApplicationException(session.response, 401);
    }

}
