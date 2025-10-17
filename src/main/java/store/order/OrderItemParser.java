package store.order;

import store.product.ProductOut;

public final class OrderItemParser {

    private OrderItemParser() { /* util class */ }

    public static OrderItem to(OrderItemIn in) {
        return in == null ? null :
            OrderItem.builder()
                .productId(in.idProduct())
                .quantity(in.quantity())
                .build();
    }

    public static OrderItemOut to(OrderItem i) {
        return i == null ? null :
            OrderItemOut.builder()
                .id(i.id())
                .product(ProductOut.builder().id(i.productId()).build())
                .quantity(i.quantity())
                .total(i.price() * i.quantity())
                .build();
    }
}