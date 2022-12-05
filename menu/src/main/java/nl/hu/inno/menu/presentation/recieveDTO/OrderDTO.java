package nl.hu.inno.menu.presentation.recieveDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrderDTO {

    private UUID id;
    private String date;
    private String adres;
    private List<Map<UUID, Integer>> dishes ;

    public String getDate() {
        return date;
    }

    public String getAdres() {
        return adres;
    }

    public List<Map<UUID, Integer>> getDishes() {
        return dishes;
    }

    public UUID getId() {
        return id;
    }
}
