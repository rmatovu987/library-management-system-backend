package frimtechnologies.library.controllers.services.payloads;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

public class IssueRequest {
    @Schema(required = true)
    @JsonbDateFormat("dd/MM/yyyy")
    public LocalDate issueDate;

    @Schema(required = true)
    @JsonbDateFormat("dd/MM/yyyy")
    public LocalDate returnDate;

    @Schema(required=true)
    public Long bookId;

    @Schema(required=true)
    public Long studentId;

}

