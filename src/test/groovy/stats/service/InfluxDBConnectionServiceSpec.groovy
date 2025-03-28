package stats.service

import grails.config.Config
import grails.core.GrailsApplication
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class InfluxDBConnectionServiceSpec extends Specification implements ServiceUnitTest<InfluxDBConnectionService>{

    def mockConfig
    def connectionUrl = 'http://localhost:8086'

    def setup() {
        mockConfig = Mock(Config)
        service.grailsApplication = Mock(GrailsApplication){
            getConfig() >> mockConfig
        }
    }

    def cleanup() {
    }

    void "test connectionUrl is correct"() {
        given:
        service.grailsApplication.config.getProperty('influxdb.url') >> connectionUrl

        when:
        service.getConnectionURL()

        then:
        service.url == connectionUrl
    }


    void "test getConnectionURL sets url correctly"() {
        given:
        service.grailsApplication.config.getProperty('influxdb.url') >> connectionUrl

        when:
        def result = service.getConnectionURL()

        then:
        result == service
        service.url == connectionUrl
    }

}
