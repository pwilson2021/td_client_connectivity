package turntabl.io.client_connectivity.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.product.Product;
import turntabl.io.client_connectivity.reporting.ReportingModel;

import java.util.List;

@RestController
@RequestMapping(path = "api/orders")
public class OrderController {
    private final OrderService orderService;
    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;

    private ReportingModel report;
    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getOrders() { return orderService.getOrders() ;}

    @PostMapping
    public void registerNewOrder(@RequestBody Order order) {
        orderService.addNewOrder(order);
        report.setTitle("client connectivity: Order");
        report.setMsg("New Order created");
        template.convertAndSend(topic.getTopic(), report);
    }

    @DeleteMapping(path = "{orderId}")
    public void deleteOrder(@PathVariable("orderId") Integer orderId) {
        orderService.deleteOrder(orderId);
        report.setTitle("client connectivity: Order");
        report.setMsg("Order deleted.Order ID "+ orderId);
        template.convertAndSend(topic.getTopic(), report);
    }


    public void updateOrder(
            @PathVariable("orderId") Integer orderId,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) int quantity,
            @RequestParam(required = false) String orderStatus
    ) {
        orderService.updateOrder(orderId, price, quantity, orderStatus);
    }
}
