package nl.hu.inno.order.presentation.controller;

import nl.hu.inno.order.application.OrderService;
import nl.hu.inno.order.domain.exception.DishDoesntExistException;
import nl.hu.inno.order.domain.exception.OrderDoesntExistException;
import nl.hu.inno.order.presentation.dto.OrdersDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

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
        try {
            orderService.addDish(id, dishid);
        } catch (OrderDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist", e);
        }
    }

    @PostMapping("/removeDish/{id}")
    public void removeDish(@PathVariable String id, @RequestBody String dishid) {
        try {
            orderService.removeDish(dishid, id);
        } catch (OrderDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist", e);
        }
    }

    @PostMapping("/place/{id}")
    public void placeOrder(@PathVariable String id) {
        try {
            orderService.placeOrder(id);
        } catch (OrderDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist", e);
        } catch (DishDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish doesn't exist", e);
        }

    }

}
