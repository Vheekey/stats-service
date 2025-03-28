package stats.service

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Specification

@Integration
@Rollback
class InfluxDBConnectionServiceIntegrationSpec extends Specification {

    InfluxDBConnectionService influxDBConnectionService

    def setup() {
        influxDBConnectionService.init()
    }

    def cleanup() {
    }

    void "test connection are loaded from config"() {
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
