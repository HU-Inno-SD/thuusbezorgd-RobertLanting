package nl.hu.inno.menu.presentation.recieveDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderDTO {

    private UUID id;
    private String date;
    private String adres;
    private List<OrderDishDTO> dishes ;

    private String status;

    public String getDate() {
        return date;
    }

    public String getAdres() {
        return adres;
    }

    public List<OrderDishDTO> getDishes() {
        return dishes;
    }

    public UUID getId() {
        return id;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
