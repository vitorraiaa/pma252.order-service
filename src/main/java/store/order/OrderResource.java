package store.order;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public final class OrderResource {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderOut> create(
            @RequestBody OrderIn in,
            @RequestHeader(name = "User-Id", required = false) String userId,
            @RequestHeader(name = "id-account", required = false) String idAccount) {

        String uid = userId != null ? userId : idAccount;
        Order created = service.create(OrderParser.to(in, uid));
        return ResponseEntity.ok(OrderParser.to(created));
    }

    @GetMapping
    public ResponseEntity<List<store.order.OrderSummary>> findAll(
            @RequestHeader(name = "User-Id", required = false) String userId,
            @RequestHeader(name = "id-account", required = false) String idAccount) {

        String uid = userId != null ? userId : idAccount;

        return ResponseEntity.ok(
                service.findAllByUser(uid)
                       .stream()
                       .map(OrderParser::toSummary)
                       .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOut> findByIdOrder(
            @PathVariable("id") String id,
            @RequestHeader(name = "User-Id", required = false) String userId,
            @RequestHeader(name = "id-account", required = false) String idAccount) {

        String uid = userId != null ? userId : idAccount;

        Order o = service.findByIdOrder(id, uid);
        return (o == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(OrderParser.to(o));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOut> update(
            @PathVariable("id") String id,
            @RequestBody OrderIn in,
            @RequestHeader(name = "User-Id", required = false) String userId,
            @RequestHeader(name = "id-account", required = false) String idAccount) {

        String uid = userId != null ? userId : idAccount;

        Order updated = service.update(OrderParser.to(in, uid).id(id));
        return (updated == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(OrderParser.to(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") String id,
            @RequestHeader(name = "User-Id", required = false) String userId,
            @RequestHeader(name = "id-account", required = false) String idAccount) {

        String uid = userId != null ? userId : idAccount;

        service.delete(id, uid);
        return ResponseEntity.noContent().build();
    }
}
