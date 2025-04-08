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
        log.info("Book created message received: "+message)

        ObjectMapper objectMapper = new ObjectMapper();
        BookCreatedDTO bookCreatedDTO = objectMapper.readValue(message, BookCreatedDTO.class)
        log.info("Book created message to save: "+bookCreatedDTO)

        bookService.saveBook(
                bookCreatedDTO.getBookId(),
                bookCreatedDTO.getTitle(),
                bookCreatedDTO.getAuthor(),
                bookCreatedDTO.getIsbn(),
                bookCreatedDTO.getStatus(),
                bookCreatedDTO.getCreatedAt()
        )
    }
}