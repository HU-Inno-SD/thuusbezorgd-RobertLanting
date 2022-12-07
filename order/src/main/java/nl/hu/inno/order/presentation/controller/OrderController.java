package nl.hu.inno.order.presentation.controller;

import nl.hu.inno.order.application.OrderService;
import nl.hu.inno.order.presentation.dto.OrdersDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("")
    public OrdersDTO getOrders() {
        return new OrdersDTO(orderService.getOrders());
    }

    @PostMapping("/create")
    public void createOrder(@RequestBody String adres) {
        orderService.createOrder(adres);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/addDish/{id}")
    public void addDish(@PathVariable String id, @RequestBody String dishid) {
        orderService.dishExists(dishid, id);
    }

    @PostMapping("/place/{id}")
    public void placeOrder(@PathVariable String id) {
        orderService.checkDishes(id);

    }

}
