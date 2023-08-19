package by.system.news.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CommentDto(Long id,
                         LocalDateTime time,
                         String text,
                         String username
) implements Serializable {
}
