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
        if (i == null) return null;
        int qty = (i.quantity() == null) ? 0 : i.quantity();
        double price = (i.price() == null) ? 0.0 : i.price();
        return OrderItemOut.builder()
                .id(i.id())
                .product(ProductOut.builder().id(i.productId()).build())
                .quantity(i.quantity())
                .total(price * qty)
                .build();
    }
}
