package store.order;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class OrderParser {

    private static final DateTimeFormatter SUMMARY_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneOffset.UTC);

    private OrderParser() { /* util class */ }

    public static Order to(OrderIn in, String userId) {
        if (in == null) return null;

        List<OrderItem> items = (in.items() == null)
                ? List.of()
                : in.items().stream().map(OrderItemParser::to).toList();

        return Order.builder()
                .idUser(userId)
                .items(items)
                .build();
    }

    public static OrderOut to(Order o) {
        if (o == null) return null;

        List<OrderItemOut> items = (o.items() == null)
                ? List.of()
                : o.items().stream().map(OrderItemParser::to).toList();

        return OrderOut.builder()
                .id(o.id())
                .date(o.date() != null ? SUMMARY_FORMATTER.format(Instant.ofEpochMilli(o.date().getTime())) : null)
                .total(o.total())
                .items(items)
                .build();
    }

    public static store.order.OrderSummary toSummary(Order o) {
        if (o == null) return null;
        String date = null;
        if (o.date() != null) {
            date = SUMMARY_FORMATTER.format(Instant.ofEpochMilli(o.date().getTime()));
        }
        return new store.order.OrderSummary(o.id(), date, o.total());
    }
}