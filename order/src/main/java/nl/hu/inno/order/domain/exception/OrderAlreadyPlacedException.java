package nl.hu.inno.order.domain.exception;

public class OrderAlreadyPlacedException extends RuntimeException{
    public OrderAlreadyPlacedException(String message) {
        super(message);
    }
}
