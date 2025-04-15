package stats.service

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

import java.time.Instant

class BooksCreatedSpec extends Specification implements DomainUnitTest<BooksCreated> {

    void "test should create valid BookCreated entity"(){
        given:
        def createdAt = Instant.now()
        def bookId = "test-d1ea6a02-37c4-4329-94ee-7739b918404b";
        def title = "The Great Gasby";
        def author = "F. Scott Fitzgerald";
        def description = "A historical fiction";
        def location= "Shelf-A2";
        def bookType= "BOOK";
        def fine= 10.00;
        def isbn = "9780333791035";
        def category= "FICTION";
        def status= "AVAILABLE";

        def bookCreated = new BooksCreated(
                bookId: bookId,
                title: title,
                author: author,
                isbn: isbn,
                createdAt: createdAt,
                status: status
        )

        expect:
        bookCreated.getMeasurementName() == BooksCreated.MEASUREMENT_NAME
        bookCreated.getCreatedAt() == createdAt
        bookCreated.getBookId() == bookId
        bookCreated.getIsbn() == isbn
        bookCreated.getStatus() == status
        bookCreated.getAuthor() == author
    }

     void "test domain constraints"() {
        given:
        BooksCreated booksCreated = new BooksCreated()

        when:
        def valid = booksCreated.validate()

         then:
         !valid
         booksCreated.errors.getFieldError("bookId")
         booksCreated.errors.getFieldError("title")
         booksCreated.errors.getFieldError("author")
         booksCreated.errors.getFieldError("isbn")
         booksCreated.errors.getFieldError("status")
         booksCreated.errors.getFieldError("createdAt")
     }

    def "test should fail validation when fields are blank"() {
        given:
        def booksCreated = new BooksCreated(
                createdAt: Instant.now(),
                bookId: bookId,
                title: title,
                author: author,
                isbn: isbn,
                status: status
        )

        when:
        def valid = booksCreated.validate()

        then:
        valid == expectedValid

        where:
        bookId     | title     | author     | isbn     | status     || expectedValid
        ""         | "Title"   | "Author"   | "ISBN"   | "Status"   || false
        "book-123" | ""        | "Author"   | "ISBN"   | "Status"   || false
        "book-123" | "Title"   | ""         | "ISBN"   | "Status"   || false
        "book-123" | "Title"   | "Author"   | ""       | "Status"   || false
        "book-123" | "Title"   | "Author"   | "ISBN"   | ""         || false
        "book-123" | "Title"   | "Author"   | "ISBN"   | "Status"   || true
    }
}
