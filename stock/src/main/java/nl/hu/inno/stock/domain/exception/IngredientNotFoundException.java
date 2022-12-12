package nl.hu.inno.stock.domain.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(String id) {
        super("Could not find ingredient " + id);
    }
}
