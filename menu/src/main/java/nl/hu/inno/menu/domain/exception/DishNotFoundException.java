package nl.hu.inno.menu.domain.exception;

public class DishNotFoundException extends RuntimeException {

    public DishNotFoundException(String id) {
        super("Could not find dish " + id);
    }
}
