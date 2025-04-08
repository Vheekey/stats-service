package stats.service;

import lombok.Data;

@Data
public class BookCreatedDTO {
    private String bookId;
    private String title;
    private String author;
    private String description;
    private String isbn;
    private String status;
    private String createdAt;
}
