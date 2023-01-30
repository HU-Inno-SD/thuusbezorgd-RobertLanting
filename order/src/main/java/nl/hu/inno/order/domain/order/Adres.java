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
    }

    public String get() {
        return adres;
    }
}
