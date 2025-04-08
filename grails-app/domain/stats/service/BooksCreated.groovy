package stats.service

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.time.Instant

@Measurement(name = "books_created")
class BooksCreated extends InfluxEntity {

    private static final MEASUREMENT_NAME = "books_created"

    @Column(timestamp = true)
    Instant createdAt

    @Column(tag = true)
    String bookId

    @Column
    String title

    @Column(tag = true)
    String author

    @Column(tag = true)
    String isbn

    @Column(tag = true)
    String status

    static constraints = {
        bookId blank: false
        title blank: false
        author blank: false
        isbn blank: false
        status blank: false
        createdAt nullable: false
    }

    @Override
    String getMeasurementName() {
        return MEASUREMENT_NAME
    }
}
