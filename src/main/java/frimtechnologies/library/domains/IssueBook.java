package frimtechnologies.library.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class IssueBook extends PanacheEntity {

    @Column(nullable = false)
    public LocalDate issueDate;

    @Column(nullable = false)
    public LocalDate returnDate;

    public String status = "ISSUED";

    public LocalDateTime entryTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(nullable = false)
    public Book book;

    @ManyToOne
    @JoinColumn(nullable = false)
    public Student student;

    public IssueBook() {
    }

    public IssueBook(LocalDate issueDate, LocalDate returnDate, Book book, Student student) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.book = book;
        this.student = student;
    }
}
