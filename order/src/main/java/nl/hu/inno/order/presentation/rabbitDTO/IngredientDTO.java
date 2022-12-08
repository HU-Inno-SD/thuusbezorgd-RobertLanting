package nl.hu.inno.order.presentation.rabbitDTO;

public class IngredientDTO {

    private String id;

    private int amount;

    public IngredientDTO() {
    }

    public IngredientDTO(String name, int amount) {
        this.id = name;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String name) {
        this.id = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
