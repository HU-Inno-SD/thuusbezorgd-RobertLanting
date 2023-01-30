package nl.hu.inno.order.domain.orderdish;

public class Amount {

    private int amount;

    public Amount(int amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public void addAmount(int amount) {
        validateAmount(this.amount + amount);
        this.amount += amount;
    }

    public void removeAmount(int amount) {
        validateAmount(this.amount - amount);
        this.amount -= amount;
    }

    private void validateAmount(int amount) {
        if (amount > 50) {
            throw new IllegalArgumentException("Amount cannot be above 50");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be below 0");
        }
    }

    public int get() {
        return amount;
    }
}
