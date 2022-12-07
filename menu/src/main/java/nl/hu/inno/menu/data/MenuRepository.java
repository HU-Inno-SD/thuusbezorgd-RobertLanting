package nl.hu.inno.menu.data;

import nl.hu.inno.menu.domain.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MenuRepository extends MongoRepository<Dish, UUID> {
}
