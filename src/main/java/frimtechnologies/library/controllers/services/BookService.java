package frimtechnologies.library.controllers.services;

import frimtechnologies.library.controllers.services.payloads.BookRequest;
import frimtechnologies.library.domains.Book;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.WebApplicationException;
import java.util.List;

@ApplicationScoped
public class BookService {

    public Book create(BookRequest request) {
        Book book = new Book(
                request.title,
                request.bookCode,
                request.serialNumber,
                request.author,
                request.year
        );
        book.persist();

        return book;
    }

    public Book update(Long id, BookRequest request) {
        Book book = Book.findById(id);
        if (book == null) throw new WebApplicationException("Invalid book selected", 404);

        book.title = request.title;
        book.serialNumber = request.serialNumber;
        book.bookCode = request.bookCode;
        book.author = request.author;
        book.year = request.year;

        return book;
    }

    public List<Book> get() {
        return Book.listAll();
    }

    public void delete(Long id){
        Book book = Book.findById(id);
        if (book == null) throw new WebApplicationException("Invalid book selected", 404);

        book.delete();
    }
}
