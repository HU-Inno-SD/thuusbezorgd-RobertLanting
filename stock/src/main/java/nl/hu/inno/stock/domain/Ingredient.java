package nl.hu.inno.stock.domain;

import java.util.UUID;

public class Ingredient {

    private String id;
    private int amount;

    public Ingredient(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
