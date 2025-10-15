package store.order;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = { "store.order", "store.product" })
@EnableFeignClients(basePackages = "store.product")
public final class OrderApplication {

    private OrderApplication() { /* no instances */ }

    public static void main(String[] args) {
        new SpringApplicationBuilder(OrderApplication.class)
            .properties("spring.application.name=order-service")
            .run(args);
    }
}
