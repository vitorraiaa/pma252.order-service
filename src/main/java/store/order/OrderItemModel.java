package store.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Entity(name = "OrderItem") 
@Table(name = "item", schema = "store_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(fluent = true, chain = true)
@EqualsAndHashCode(of = "idItem")
@ToString(onlyExplicitlyIncluded = true)
public class OrderItemModel {

    @Id
    @Column(name = "id_item", updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @ToString.Include
    private String idItem;

    @Column(name = "id_product", nullable = true)
    @ToString.Include
    private String idProduct;

    @Column(name = "quantity", nullable = true)
    @ToString.Include
    private Integer quantity;

    @Column(name = "item_price", nullable = true)
    @ToString.Include
    private Double itemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_order")
    @ToString.Exclude
    private OrderModel order;


    public OrderItemModel(OrderItem source, OrderModel order) {
        this.idItem = source.id();
        this.idProduct = source.productId();
        this.quantity = source.quantity();
        this.itemPrice = source.price();
        this.order = order;
    }

    public static OrderItemModel of(OrderItem source, OrderModel order) {
        return new OrderItemModel(source, order);
    }


    public OrderItem to() {
        return OrderItem.builder()
            .id(idItem)
            .productId(idProduct)
            .quantity(quantity)
            .price(itemPrice)
            .build();
    }


    public double total() {
        double q = quantity == null ? 0 : quantity;
        double p = itemPrice == null ? 0 : itemPrice;
        return q * p;
    }
}