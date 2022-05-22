package frimtechnologies.library.domains;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public class Book extends PanacheEntity {

    @Column(nullable = false)
    public String title;

    @Column(nullable = true)
    public String bookCode;

    @Column(nullable = true)
    public String serialNumber;

    @Column(nullable = true)
    public String author;

    @Column(nullable = true)
    public String year;

    public LocalDateTime entryTime = LocalDateTime.now();

    public Book() {
    }

    public Book(String title, String bookCode, String serialNumber, String author, String year) {
        this.title = title;
        this.bookCode = bookCode;
        this.serialNumber = serialNumber;
        this.author = author;
        this.year = year;
    }
}
