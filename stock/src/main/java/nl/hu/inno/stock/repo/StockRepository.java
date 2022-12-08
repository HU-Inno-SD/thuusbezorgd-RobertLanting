package nl.hu.inno.stock.repo;

import nl.hu.inno.stock.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Ingredient, String> {
}

