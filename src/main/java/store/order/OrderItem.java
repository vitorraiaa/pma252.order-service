package store.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
public class OrderItem {
    private String id;
    private String productId;
    private Integer quantity;
    private Double price;
}
