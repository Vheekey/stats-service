package stats.service

import com.influxdb.annotations.Column
import com.influxdb.annotations.Measurement
import java.time.Instant

@Measurement(name = "users_created")
class UsersCreated extends InfluxEntity{

    public static final MEASUREMENT_NAME = "users_created"

    @Column(timestamp = true)
    Instant dateCreated

    @Column(tag = true)
    Integer userId

    @Column(tag = true)
    String email

    @Column
    String name

    @Column
    Boolean active

    static constraints = {
        userId blank : false
        dateCreated nullable : false
        email blank: false
        name blank: false
        active blank: false
    }

    @Override
    String getMeasurementName() {
        return MEASUREMENT_NAME
    }
}