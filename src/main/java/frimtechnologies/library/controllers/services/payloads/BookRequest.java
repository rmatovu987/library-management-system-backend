package frimtechnologies.library.controllers.services.payloads;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.persistence.Column;

public class BookRequest {
    @Schema(required = true)
    public String title;

    public String bookCode;

    public String serialNumber;

    public String author;

    public String year;
}
