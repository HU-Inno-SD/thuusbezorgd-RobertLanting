package nl.hu.inno.menu.repo;

import nl.hu.inno.menu.domain.Dish;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface MenuRepository extends MongoRepository<Dish, UUID> {
}
