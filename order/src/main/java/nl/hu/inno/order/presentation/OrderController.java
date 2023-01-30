package nl.hu.inno.order.presentation;

import nl.hu.inno.order.domain.exception.*;
import nl.hu.inno.order.domain.order.Order;
import nl.hu.inno.order.domain.orderdish.OrderDish;
import nl.hu.inno.order.presentation.dto.OrdersDTO;
import nl.hu.inno.order.presentation.rabbitDTO.IngredientDTO;
import nl.hu.inno.order.presentation.rabbitDTO.IngredientListDTO;
import nl.hu.inno.order.presentation.rabbitDTO.MenuDTO;
import nl.hu.inno.order.presentation.rabbitDTO.StockDTO;
import nl.hu.inno.order.repo.OrderRepository;
import nl.hu.inno.order.repo.RabbitMQJsonProducer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import nl.hu.inno.order.presentation.dto.OrderDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final RabbitMQJsonProducer jsonProducer;

    private final OrderRepository orderRepository;

    private MenuDTO menu;
    private StockDTO stock;

    public OrderController(OrderRepository orderRepository, RabbitMQJsonProducer jsonProducer) {
        this.orderRepository = orderRepository;
        this.jsonProducer = jsonProducer;
        jsonProducer.getMenu();
        jsonProducer.getStock();
    }

    @GetMapping("")
    public OrdersDTO getOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            orders.add(new OrderDTO(order));
        }
        return new OrdersDTO(orders);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@PathVariable UUID id) {
        Order order = orderRepository.findByOrderid(id);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        }
        return new OrderDTO(order);
    }

    @PostMapping("")
    public void createOrder(@RequestBody String adres) {
        Order order = new Order(adres);
        orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        orderRepository.deleteByOrderid(id);
    }

    @PostMapping("/dish/{id}")
    public void addDish(@PathVariable UUID id, @RequestBody UUID dishid) {
        try {
            Order order = orderRepository.findByOrderid(id);
            order.addDish(dishid);
            orderRepository.save(order);
        } catch (OrderDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist", e);
        }
    }

    @DeleteMapping("/dish/{id}")
    public void removeDish(@PathVariable UUID id, @RequestBody UUID dishid) {
        try {
            Order order = orderRepository.findByOrderid(id);
            order.removeDish(dishid);
            orderRepository.save(order);
        } catch (OrderDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist", e);
        }
    }

    @PostMapping("/{id}")
    public void placeOrder(@PathVariable UUID id) {
        try {
            Order order = orderRepository.findByOrderid(id);
            if (order == null) {
                throw new OrderDoesntExistException("Order doesn't exist");
            }
            checkIfDishesExist(order);
            checkIfIngredientsInStock(order);
            takeingredientsFromStock(getIngredientsFromOrder(order));
            order.place();
            orderRepository.save(order);
        } catch (OrderDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order doesn't exist", e);
        } catch (DishDoesntExistException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish doesn't exist", e);
        } catch (OrderAlreadyPlacedException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is already placed", e);
        } catch (OrderCancelledException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order is cancelled", e);
        } catch (IngredientNotInStockException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ingredient not in stock", e);
        }

    }

    private void takeingredientsFromStock(IngredientListDTO ingredientsFromOrder) {
        jsonProducer.sendIngredients(ingredientsFromOrder);
        stock.getStock()
                .forEach(s -> s.setAmount(s.getAmount() - ingredientsFromOrder.getIngredients()
                        .stream().filter(i -> i.getId().equals(s.getId())).findFirst().get().getAmount()));
    }

    public void checkIfDishesExist(Order order) {
        if (!menu.getMenu().stream().allMatch(d -> order.getDishes().stream().anyMatch(dish -> dish.getDish().equals(d.getId())))) {
            throw new DishDoesntExistException("Dish doesn't exist");
        }
    }

    public void checkIfIngredientsInStock(Order order) {
        if (!getIngredientsFromOrder(order).getIngredients().stream().allMatch(i -> stock.getStock().stream().anyMatch(s -> s.getId().equals(i.getId()) && s.getAmount() >= i.getAmount()))) {
            order.cancel();
            orderRepository.save(order);
            throw new IngredientNotInStockException("Ingredient not in stock");
        }
    }

    public IngredientListDTO getIngredientsFromOrder(Order order) {
        IngredientListDTO ingredients = new IngredientListDTO();
        for (OrderDish orderDish : order.getDishes()) {
            for (String ingredient : getIngredientsFromDish(orderDish.getDish())) {
                if (ingredients.getIngredients().stream().anyMatch(ingredientDTO -> ingredientDTO.getId().equals(ingredient))) {
                    ingredients.getIngredients().stream()
                            .filter(ingredientDTO -> ingredientDTO.getId().equals(ingredient))
                            .forEach(ingredientDTO -> ingredientDTO.setAmount(ingredientDTO.getAmount() + orderDish.getAmount().get()));
                } else {
                    ingredients.getIngredients().add(new IngredientDTO(ingredient, orderDish.getAmount().get()));
                }
            }
        }
        return ingredients;
    }

    public List<String> getIngredientsFromDish(UUID dishid) {
        return menu.getMenu().stream().filter(d -> d.getId().equals(dishid)).findFirst().get().getIngredients();
    }

    public void updateMenu(MenuDTO menuDTO) {
        System.out.println("Menu updated");
        this.menu = menuDTO;
    }
    public void updateStock(StockDTO stockDTO) {
        System.out.println("Stock updated");
        this.stock = stockDTO;
    }

}
