package stats.service

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.test.context.TestPropertySource
import spock.lang.Specification

@TestPropertySource(properties = ["spring.kafka.enable=false"])
@Integration
@Rollback
class InfluxDBConnectionServiceIntegrationSpec extends Specification {

    InfluxDBConnectionService influxDBConnectionService

    def setup() {
        influxDBConnectionService.init()
    }

    def cleanup() {
    }

    void "test influxdb connection are loaded from config"() {
        expect:
        influxDBConnectionService.url
        influxDBConnectionService.databaseName
        influxDBConnectionService.databasePassword
        influxDBConnectionService.databaseUser
        influxDBConnectionService.token
        influxDBConnectionService.org
    }

    void "test actual connection to influxdb"() {
        when: "we attempt to create a connection"
        def result = influxDBConnectionService.connect()

        then: "connection is established"
        result === true
    }
}
