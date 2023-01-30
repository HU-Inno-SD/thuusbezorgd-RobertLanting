package nl.hu.inno.order.domain.order;

public class Adres {

    private String adres;

    public Adres(String adres) {
        validateAdres(adres);
        this.adres = adres;
    }

    private void validateAdres(String adres) {
        if (adres == null) {
            throw new IllegalArgumentException("Adres cannot be null");
        }
        if (!adres.matches("^[a-zA-Z0-9 ]*$")) {
            throw new IllegalArgumentException("Adres can only contain letters, numbers and spaces");
        }
        if (adres.length() > 50) {
            throw new IllegalArgumentException("Adres cannot be longer than 50 characters");
        }
        if (adres.length() < 5) {
            throw new IllegalArgumentException("Adres cannot be shorter than 5 characters");
        }
    }

    public String get() {
        return adres;
    }
}
