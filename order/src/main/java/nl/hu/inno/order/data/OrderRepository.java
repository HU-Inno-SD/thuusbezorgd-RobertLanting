package nl.hu.inno.order.data;

import nl.hu.inno.order.domain.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface OrderRepository extends MongoRepository<Order, UUID> {
}

