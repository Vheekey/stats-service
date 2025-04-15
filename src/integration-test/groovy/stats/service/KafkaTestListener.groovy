package stats.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaTestListener {
    static List<String> messages = [];

    @KafkaListener(topics = "test_connection", groupId = "test-group")
    static void listen(String message) {
        messages.add(message)
    }
}
