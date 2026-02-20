package dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ItemDTO {
    private Long itemId;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private Long sellerId;
    private Long categoryId;
}
