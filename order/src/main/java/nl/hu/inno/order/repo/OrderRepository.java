package nl.hu.inno.order.repo;

import nl.hu.inno.order.domain.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    Order findByOrderid(UUID orderid);

    void deleteByOrderid(UUID orderid);

    List<Order> findAll();
}

