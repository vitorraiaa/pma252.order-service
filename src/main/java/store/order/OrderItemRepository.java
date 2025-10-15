package store.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemModel, String> {

    @Transactional(readOnly = true)
    List<OrderItemModel> findAllByOrder_IdOrder(String idOrder);
}
