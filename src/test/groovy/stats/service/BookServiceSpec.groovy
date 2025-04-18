package stats.service

import grails.testing.gorm.DomainUnitTest
import grails.testing.services.ServiceUnitTest
import org.springframework.test.annotation.Rollback
import spock.lang.Specification
import stats.repositories.InfluxDBRepository

@Rollback
class BookServiceSpec extends Specification implements ServiceUnitTest<BookService>, DomainUnitTest<BooksCreated> {

    BookService service
    InfluxDBRepository influxDBRepository

    def setup() {
        influxDBRepository = Mock(InfluxDBRepository)
        service = new BookService(influxDBRepository)
    }

    def cleanup() {
    }

    void "test book is saved"() {
        given: "book is saved"
        def createdAt = "2025-04-14T03:10:18.38783";
        def bookId = "test-d1ea6a02-37c4-4329-94ee-7739b918404b";
        def title = "The Great Gasby";
        def author = "F. Scott Fitzgerald";
        def description = "A historical fiction";
        def location = "Shelf-A2";
        def bookType = "BOOK";
        def fine = 10.00;
        def isbn = "9780333791035";
        def category = "FICTION";
        def status = "AVAILABLE";

        when: "saving the book"
        def isBookSaved = service.saveBook(
                bookId,
                title,
                author,
                isbn,
                status,
                createdAt
        )

        then: "book should be saved successfully"
        isBookSaved == true
    }

    void "test repository exception handling"() {
        given: "valid book data"
        def createdAt = ""
        def bookId = "test-123"
        def title = null
        def author = "Test Author"
        def isbn = "1234567890"
        def status = "AVAILABLE"

        when: "repository throws an exception during save"
        def result = service.saveBook(bookId, title, author, isbn, status, createdAt)

        then: "exception is propagated"
        result != true
        thrown(RuntimeException)
    }

    void "test saveBook with null fields"() {
        given: "invalid book data that should contain non-null required fields"
        def createdAt = "2025-04-14T03:10:18.38783"
        when: "saving data with null fields"
        service.saveBook(bookId, title, author, isbn, status, createdAt)

        then: "validation should fail"
        thrown(IllegalArgumentException)

        where:
        bookId     | title       | author        | isbn         | status
        null       | "Test Book" | "Test Author" | "1234567890" | "AVAILABLE"
        "test-123" | null        | "Test Author" | "1234567890" | "AVAILABLE"
        "test-123" | "Test Book" | null          | "1234567890" | "AVAILABLE"
        "test-123" | "Test Book" | "Test Author" | null         | "AVAILABLE"
    }
}
