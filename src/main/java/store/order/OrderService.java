package store.order;

import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import store.product.ProductController;
import store.product.ProductOut;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductController productController;

    @Cacheable(value = "orders", key = "#idOrder + '-' + #userId")
    @Transactional(readOnly = true)
    public Order findByIdOrder(String idOrder, String userId) {
        OrderModel model = orderRepository.findByIdOrderAndIdUser(idOrder, userId);
        return model == null ? null : model.to();
    }

    @Transactional(readOnly = true)
    public List<Order> findAllByUser(String userId) {
        return orderRepository.findAllByIdUser(userId)
                              .stream()
                              .map(OrderModel::to)
                              .toList();
    }

    @Transactional
    public Order create(Order order) {
        order.date(new Date());


        if (order.items() != null) {
            for (OrderItem i : order.items()) {
                i.price(fetchPrice(i.productId()));
            }
        }

        order.total(calculateTotal(order.items()));


        OrderModel saved = orderRepository.save(new OrderModel(order));
        return saved.to();
    }

    @Transactional
    public Order update(Order order) {
        OrderModel existing = orderRepository
                .findByIdOrderAndIdUser(order.id(), order.idUser());

        if (existing == null) return null;

        existing.date(new Date());
        existing.total(calculateTotal(order.items()));

        existing.items().clear();
        persistItems(order.items(), existing);

        orderRepository.save(existing);
        return existing.to();
    }

    @Transactional
    public void delete(String idOrder, String userId) {
        OrderModel existing = orderRepository.findByIdOrderAndIdUser(idOrder, userId);
        if (existing != null) orderRepository.delete(existing);
    }

    private void persistItems(List<OrderItem> items, OrderModel order) {
        if (items == null) return;

        for (OrderItem i : items) {
            i.price(fetchPrice(i.productId()));      
            OrderItemModel im = new OrderItemModel(i, order);
            order.items().add(im);
        }
    }

    private Double calculateTotal(List<OrderItem> items) {
        if (items == null) return 0.0;
        return items.stream()
                    .mapToDouble(i -> fetchPrice(i.productId()) * i.quantity())
                    .sum();
    }

    private Double fetchPrice(String productId) {
        ProductOut product = productController.findById(productId).getBody();
        return product != null ? product.price() : 0.0;
    }
}
