

import grails.core.GrailsApplication
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Conditional
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

@Conditional(KafkaCondition)
@Slf4j
@Configuration
@EnableKafka
class KafkaConfig {

    @Autowired
    GrailsApplication grailsApplication;

    @Conditional(KafkaCondition)
    @Bean
    Map<String, Object> consumerConfigs() {
        Map<String, Object> props = [
                (ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)       : grailsApplication.config.getProperty('spring.kafka.bootstrap-servers'),
                (ConsumerConfig.GROUP_ID_CONFIG)                : grailsApplication.config.getProperty('spring.kafka.consumer.group-id'),
                (ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)  : StringDeserializer.class,
                (ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG): StringDeserializer.class,
                (ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)       : grailsApplication.config.getProperty('spring.kafka.consumer.auto-offset-reset')
        ] as Map<String, Object>

        return props
    }

    @Conditional(KafkaCondition)
    @Bean
    ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs())
    }

    @Conditional(KafkaCondition)
    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>()
        factory.setConsumerFactory(consumerFactory())
        return factory
    }
}
