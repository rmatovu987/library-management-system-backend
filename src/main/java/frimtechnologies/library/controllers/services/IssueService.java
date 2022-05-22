package frimtechnologies.library.controllers.services;

import frimtechnologies.library.controllers.services.payloads.IssueRequest;
import frimtechnologies.library.domains.Book;
import frimtechnologies.library.domains.IssueBook;
import frimtechnologies.library.domains.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;

@ApplicationScoped
public class IssueService {

    public IssueBook issue(IssueRequest request) {
        Book book = Book.findById(request.bookId);
        if (book == null) throw new WebApplicationException("Invalid book selected", 404);

        Student student = Student.findById(request.studentId);
        if (student == null) throw new WebApplicationException("Invalid student selected", 404);

        IssueBook issue = new IssueBook(
                request.issueDate,
                request.returnDate,
                book,
                student
        );
        issue.persist();

        return issue;
    }

    public IssueBook returnBook(Long id){
        IssueBook issue = IssueBook.findById(id);
        if(issue == null) throw new WebApplicationException("Invalid issue selected",404);

        issue.status = "RETURNED";

        return issue;
    }
}
