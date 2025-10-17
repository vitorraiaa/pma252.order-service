package store.order;

import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Table(name = "orders", schema = "orders")
@Getter
@Setter
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(of = "idOrder")
@ToString(onlyExplicitlyIncluded = true)
public class OrderModel {

    @Id
    @Column(name = "id_order")
    @GeneratedValue(strategy = GenerationType.UUID)
    @ToString.Include
    private String idOrder;

    @Column(name = "date")
    @ToString.Include
    private Date date;

    @Column(name = "total")
    @ToString.Include
    private Double total;

    @Column(name = "id_user")
    @ToString.Include
    private String idUser;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<OrderItemModel> items = new ArrayList<>();

    /** Construtor de mapeamento (lógica inalterada) */
    public OrderModel(Order source) {
        this.idOrder = source.id();
        this.idUser  = source.idUser();
        this.date    = source.date();
        this.total   = source.total();
        if (source.items() != null) {
            for (OrderItem item : source.items()) {
                this.items.add(new OrderItemModel(item, this));
            }
        }
    }

    /** Factory opcional (variação sem impacto na lógica) */
    public static OrderModel of(Order source) {
        return new OrderModel(source);
    }

    /** Mapper reverso para domínio (lógica inalterada) */
    public Order to() {
        List<OrderItem> domainItems = (items == null)
                ? new ArrayList<>()
                : items.stream()
                       .map(OrderItemModel::to)
                       .collect(Collectors.toList());

        return Order.builder()
                .id(idOrder)
                .idUser(idUser)
                .date(date)
                .total(total)
                .items(domainItems)
                .build();
    }
}
