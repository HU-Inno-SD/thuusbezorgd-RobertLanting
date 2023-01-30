package nl.hu.inno.order.domain.order;

import nl.hu.inno.order.domain.exception.OrderAlreadyPlacedException;
import nl.hu.inno.order.domain.exception.OrderCancelledException;
import nl.hu.inno.order.domain.orderdish.OrderDish;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document
public class Order {

    @Id
    private String id;
    private UUID orderid;
    private Date date;
    private Adres adres;
    private List<OrderDish> dishes ;

    private OrderStatus status;

    public Order() {
    }

    public Order(String adres) {
        this.date = new Date(LocalDateTime.now());
        this.adres = new Adres(adres);
        this.orderid = UUID.randomUUID();
        this.dishes = new ArrayList<>();
        this.status = OrderStatus.CREATED;
    }

    public Date getDate() {
        return date;
    }

    public Adres getAdres() {
        return adres;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public UUID getOrderid() {
        return orderid;
    }

    public void addDish(UUID dish) {
        for (OrderDish d : dishes) {
            if (d.getDish().equals(dish)) {
                d.getAmount().addAmount(1);
                return;
            }
        }
        dishes.add(new OrderDish(dish, 1));
    }

    public void removeDish(UUID dish) {
        for (OrderDish d : dishes) {
            if (d.getDish().equals(dish)) {
                d.getAmount().removeAmount(1);
                if (d.getAmount().get() == 0) {
                    dishes.remove(d);
                }
                return;
            }
        }
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void place() {

        if (status == OrderStatus.PLACED) {
            throw new OrderAlreadyPlacedException("Order is already placed");
        }
        if (status == OrderStatus.CANCELLED) {
            throw new OrderCancelledException("Order is cancelled");
        }
        this.status = OrderStatus.PLACED;
    }

    public void cancel() {
        this.status = OrderStatus.CANCELLED;
    }
}
