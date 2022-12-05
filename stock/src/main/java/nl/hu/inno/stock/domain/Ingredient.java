package nl.hu.inno.stock.domain;

import java.util.UUID;

public class Ingredient {

    private String id;
    private int amount;

    public Ingredient() {
    }

    public Ingredient(String name, int amount) {
        this.id = name;
        this.amount = amount;
    }

    public String getName() {
        return id;
    }

    public void setName(String name) {
        this.id = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
