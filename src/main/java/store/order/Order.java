package store.order;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Builder(toBuilder = true)
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(of = "id")
@ToString(onlyExplicitlyIncluded = true)
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    @ToString.Include
    private String id;

    @ToString.Include
    private String idUser;

    @ToString.Include
    private Date date;

    @ToString.Include
    private Double total;

    private List<OrderItem> items;
}