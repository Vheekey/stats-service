package spring

import grails.core.GrailsApplication
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

@Slf4j
@Configuration
@EnableKafka
class KafkaConfig {

    @Autowired
    GrailsApplication grailsApplication;

    @Bean
    Map<String, Object> consumerConfigs() {
        Map<String, Object> props = [
                (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)       : grailsApplication.config.getProperty('kafka.bootstrap-servers'),
                (ConsumerConfig.GROUP_ID_CONFIG)                : grailsApplication.config.getProperty('kafka.consumer.group-id'),
                (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)  : StringDeserializer.class,
                (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG): StringDeserializer.class,
                (ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)       : grailsApplication.config.getProperty('kafka.consumer.auto-offset-reset')
        ] as Map<String, Object>

        return props
    }

    @Bean
    ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs())
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }
}
