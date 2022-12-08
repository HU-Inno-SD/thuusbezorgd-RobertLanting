package nl.hu.inno.order.domain.exception;

public class DishDoesntExistException extends RuntimeException{
    public DishDoesntExistException(String message) {
        super(message);
    }
}
