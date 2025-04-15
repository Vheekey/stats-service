package stats.service

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import org.springframework.kafka.core.KafkaTemplate
import spock.lang.Specification

@Integration
@Rollback
class KafkaConnectionServiceIntegrationSpec extends Specification {

    KafkaTestListener kafkaTestListener
    KafkaTemplate kafkaTemplate

    void "test that kafka message can be sent and received"() {
        given:
        def message = "Ping kafka in stats-service"
        kafkaTestListener.messages.clear()

        when:
        kafkaTemplate.send("test_connection", message).get()
        Thread.sleep(1000)

        then:
        kafkaTestListener.messages.size() > 0
        kafkaTestListener.messages.contains(message)
    }
}
