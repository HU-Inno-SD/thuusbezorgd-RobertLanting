package nl.hu.inno.order.domain.exception;

public class OrderDoesntExistException extends RuntimeException{
    public OrderDoesntExistException(String message) {
        super(message);
    }
}

