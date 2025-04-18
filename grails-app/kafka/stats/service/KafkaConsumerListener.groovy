package stats.service

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Slf4j
@Component
class KafkaConsumerListener {

    @Autowired
    BookService bookService

    @Autowired
    UserService userService

    @PostConstruct
    void init() {
        log.info("KafkaConsumerService initialized")
    }

    @KafkaListener(topics = "book_created", groupId = "stats-service")
    void processBooks(String message) {
        log.info("Book created message received: " + message)

        ObjectMapper objectMapper = new ObjectMapper();
        BookCreatedDTO bookCreatedDTO = objectMapper.readValue(message, BookCreatedDTO.class)
        log.info("Book created message to save: " + bookCreatedDTO)

        bookService.saveBook(
                bookCreatedDTO.getBookId(),
                bookCreatedDTO.getTitle(),
                bookCreatedDTO.getAuthor(),
                bookCreatedDTO.getIsbn(),
                bookCreatedDTO.getStatus(),
                bookCreatedDTO.getCreatedAt()
        )
    }

    @KafkaListener(topics = "user-notifications", groupId = "stats-service")
    void processUsers(String message) {
        log.info("User created message received: " + message)

        ObjectMapper objectMapper = new ObjectMapper();
        Map parsedMessage = objectMapper.readValue(message, Map.class)

        log.info("Parsed received message: " + parsedMessage)

        if (!parsedMessage instanceof Map) {
            throw new IllegalArgumentException(
                    "Parsed Message is not a map but instance of " + parsedMessage.getClass().getName()
            )
        }

        UserCreatedDTO userCreatedDTO = new UserCreatedDTO();
        userCreatedDTO.setUserId(parsedMessage.id as Integer)
        userCreatedDTO.setEmail(parsedMessage.email as String)
        userCreatedDTO.setRole(parsedMessage.role as String)
        userCreatedDTO.setActive(parsedMessage.is_active as Boolean)
        userCreatedDTO.setDateCreated(parsedMessage.created_at as String)

        log.info("User created message to save: " + userCreatedDTO)

        userService.saveUser(userCreatedDTO);
    }
}