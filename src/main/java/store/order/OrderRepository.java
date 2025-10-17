package store.order;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, String> {

    @Transactional(readOnly = true)
    @EntityGraph(attributePaths = "items", type = EntityGraph.EntityGraphType.FETCH)
    List<OrderModel> findAllByIdUser(String idUser);

    @Transactional(readOnly = true)
    @EntityGraph(attributePaths = "items", type = EntityGraph.EntityGraphType.FETCH)
    OrderModel findByIdOrderAndIdUser(String idOrder, String idUser);
}