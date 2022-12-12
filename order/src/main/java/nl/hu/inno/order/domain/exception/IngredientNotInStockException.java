package nl.hu.inno.order.domain.exception;

public class IngredientNotInStockException extends RuntimeException{
    public IngredientNotInStockException(String message) {
        super(message);
    }
}
