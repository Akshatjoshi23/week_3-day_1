
interface OrderRepository {
    void saveOrder(String product, double total);
}

interface NotificationService {
    void sendNotification(String message);
}

class MySqlOrderRepository implements OrderRepository {

    @Override
    public void saveOrder(String product, double total) {
        System.out.println(
            "Saving order to database: " + product +
            ", Total: Rs. " + total
        );
    }
}

class EmailNotificationService implements NotificationService {

    @Override
    public void sendNotification(String message) {
        System.out.println("Email notification: " + message);
    }
}

class OrderService {

    private final OrderRepository repository;
    private final NotificationService notificationService;

    public OrderService(
            OrderRepository repository,
            NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void placeOrder(String product, double price) {

        if (product == null || product.isBlank() || price < 0) {
            throw new IllegalArgumentException("Invalid order details");
        }

        double deliveryFee = 50.0;
        double total = price + deliveryFee;

        repository.saveOrder(product, total);

        notificationService.sendNotification(
            "Your order for " + product +
            " has been placed successfully."
        );

        System.out.println("Order completed successfully!");
    }
}

public class OrderManagementSystem {

    public static void main(String[] args) {

        OrderRepository repository = new MySqlOrderRepository();

        NotificationService notification =
                new EmailNotificationService();

        OrderService orderService =
                new OrderService(repository, notification);

        orderService.placeOrder("Laptop", 50000.0);
    }
}
