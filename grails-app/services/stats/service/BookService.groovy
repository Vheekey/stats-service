package stats.service

import grails.gorm.transactions.Transactional
import stats.repositories.InfluxDBRepository
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Transactional
class BookService {

    protected InfluxDBRepository influxDBRepository;

    BookService(InfluxDBRepository influxDBRepository) {
         this.influxDBRepository = influxDBRepository
    }

    def saveBook(String bookId, String title, String author, String isbn, String status, String createdAt) {
        LocalDateTime localDateTime = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        ZoneId zoneId = ZoneId.systemDefault()
        Instant createdAtInstant = localDateTime.atZone(zoneId).toInstant()
        BooksCreated booksCreated = new BooksCreated(
                bookId: bookId,
                title: title,
                author: author,
                isbn: isbn,
                status: status,
                createdAt: createdAtInstant
        )
        if (!booksCreated.validate()){
            throw new IllegalArgumentException("Invalid Book data")
        }

        influxDBRepository.save(booksCreated)
        log.info("Book data saved")

        return true
    }
}
