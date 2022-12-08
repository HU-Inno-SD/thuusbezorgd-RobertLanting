package nl.hu.inno.order.application;

import nl.hu.inno.order.domain.OrderDish;
import nl.hu.inno.order.domain.exception.DishDoesntExistException;
import nl.hu.inno.order.presentation.rabbitDTO.IngredientDTO;
import nl.hu.inno.order.presentation.rabbitDTO.IngredientListDTO;
import nl.hu.inno.order.presentation.rabbitDTO.MenuDTO;
import nl.hu.inno.order.presentation.rabbitDTO.StockDTO;
import nl.hu.inno.order.repo.OrderRepository;
import nl.hu.inno.order.domain.Order;
import nl.hu.inno.order.domain.OrderStatus;
import nl.hu.inno.order.domain.exception.OrderDoesntExistException;
import nl.hu.inno.order.rabbitmq.RabbitMQJsonProducer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final RabbitMQJsonProducer jsonProducer;

    private final OrderRepository orderRepository;

    private MenuDTO menu;

    private StockDTO stock;

    public OrderService(OrderRepository orderRepository, RabbitMQJsonProducer jsonProducer) {
        this.orderRepository = orderRepository;
        this.jsonProducer = jsonProducer;
        this.menu = new MenuDTO();
        this.stock = new StockDTO();
        jsonProducer.getMenu();
        jsonProducer.getStock();
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public void createOrder(String adres) {
        orderRepository.save(new Order(adres));
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(UUID.fromString(id));
    }

    public void addDish(String orderid, String dishid) {
        Order order = orderRepository.findById(UUID.fromString(orderid)).get();
        if (order.getDishes().stream().anyMatch(d -> d.getDish().equals(UUID.fromString(dishid)))) {
            order.getDishes().stream().filter(d -> d.getDish().equals(UUID.fromString(dishid))).forEach(d -> d.setAmount(d.getAmount() + 1));
        } else {
            order.addDish(UUID.fromString(dishid));
        }
        orderRepository.save(order);
        }

    public void removeDish(String dishid, String orderid) {
        Order order = orderRepository.findById(UUID.fromString(orderid)).orElse(null);
        if (order != null) {
            order.getDishes().stream().filter(d -> d.getDish().equals(UUID.fromString(dishid))).forEach(d -> d.setAmount(d.getAmount() - 1));
            order.getDishes().removeIf(d -> d.getAmount() <= 0);
            orderRepository.save(order);
        } else {
            throw new OrderDoesntExistException("Order doesn't exist");
        }
    }

    public boolean checkIfDishesExist(Order order) {
        return menu.getMenu().stream().allMatch(d -> order.getDishes().stream().anyMatch(dish -> dish.getDish().equals(d.getId())));
    }

    public boolean checkIfIngredientsInStock(IngredientListDTO ingredients) {
        return ingredients.getIngredients().stream().allMatch(i -> stock.getStock().stream().anyMatch(s -> s.getId().equals(i.getId()) && s.getAmount() >= i.getAmount()));
    }

    public IngredientListDTO getIngredientsFromOrder(Order order) {
        IngredientListDTO ingredients = new IngredientListDTO();
        for (OrderDish orderDishDTO : order.getDishes()) {
            for (String ingredient : getIngredientsFromDish(orderDishDTO.getDish())) {
                if (ingredients.getIngredients().stream().anyMatch(ingredientDTO -> ingredientDTO.getId().equals(ingredient))) {
                    ingredients.getIngredients().stream()
                            .filter(ingredientDTO -> ingredientDTO.getId().equals(ingredient))
                            .forEach(ingredientDTO -> ingredientDTO.setAmount(ingredientDTO.getAmount() + orderDishDTO.getAmount()));
                } else {
                    ingredients.getIngredients().add(new IngredientDTO(ingredient, orderDishDTO.getAmount()));
                }
            }
        }
        return ingredients;
    }

    public List<String> getIngredientsFromDish(UUID dishid) {
        return menu.getMenu().stream().filter(d -> d.getId().equals(dishid)).findFirst().get().getIngredients();
    }

    private void takeingredientsFromStock(IngredientListDTO ingredientsFromOrder) {
        jsonProducer.sendIngredients(ingredientsFromOrder);
        stock.getStock()
                .forEach(s -> s.setAmount(s.getAmount() - ingredientsFromOrder.getIngredients()
                        .stream().filter(i -> i.getId().equals(s.getId())).findFirst().get().getAmount()));
    }

    public void placeOrder(String id) {
        Order order = orderRepository.findById(UUID.fromString(id)).orElse(null);
        if (order == null) {
            throw new OrderDoesntExistException("Order doesn't exist");
        }
        if (!checkIfDishesExist(orderRepository.findById(UUID.fromString(id)).get())) {
            throw new DishDoesntExistException("Dish doesn't exist");
        }
        if (checkIfIngredientsInStock(getIngredientsFromOrder(order))) {
            takeingredientsFromStock(getIngredientsFromOrder(order));
            order.setStatus(OrderStatus.PLACED);
            orderRepository.save(order);
        };
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




