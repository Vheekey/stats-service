package stats.service

import com.influxdb.annotations.Column
import java.time.Instant

abstract class InfluxEntity {
    @Column(timestamp = true)
    Instant createdAt = Instant.now()

    abstract String getMeasurementName()
}
