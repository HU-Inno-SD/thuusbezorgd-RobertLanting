package nl.hu.inno.order.domain.exception;

public class OrderCancelledException extends RuntimeException{
    public OrderCancelledException(String message) {
        super(message);
    }
}
