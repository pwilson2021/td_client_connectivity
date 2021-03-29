package turntabl.io.client_connectivity.order;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.portfolio.Portfolio;
import turntabl.io.client_connectivity.portfolio.PortfolioService;
import turntabl.io.client_connectivity.product.Product;
import turntabl.io.client_connectivity.reporting.ReportingModel;
import turntabl.io.client_connectivity.product.ProductService;
import turntabl.io.client_connectivity.soap.SoapClient;
import turntabl.io.client_connectivity.user.User;
import turntabl.io.client_connectivity.user.UserService;



import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(path = "api/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final PortfolioService portfolioService;
    private final ProductService productService;

    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;
    @Autowired
    SoapClient soapClient;
    ObjectMapper mapper = new ObjectMapper();

    private ReportingModel report;

    @Autowired
    public OrderController(
            OrderService orderService,
                           PortfolioService portfolioService,
                            UserService userService,
                           ProductService productService

    ){
        this.orderService = orderService;
        this.userService = userService;
        this.portfolioService = portfolioService;
        this.productService = productService;
    }

    @GetMapping
    public List<Order> getOrders() { return orderService.getOrders() ;}

    @PostMapping
    public void registerNewOrder(@RequestBody OrderRequest orderRequest) throws JsonProcessingException {
        User user = userService.findUserById(orderRequest.getUser_id());
        Portfolio portfolio = portfolioService.findPortfolioById(orderRequest.getPortfolio_id());
        Product product = productService.findProductById(orderRequest.getProduct_id());
        Order order = new Order(
                orderRequest.getPrice(),
                orderRequest.getQuantity(),
                orderRequest.getOrder_type(),
                "pending",
                user, portfolio, product);

        //        send order to reporting service

        int orderId = orderService.addNewOrder(order);
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString("New order created:  "+orderId));
        soapClient.orderResponse(orderId,orderRequest.getUser_id(),orderRequest.getProduct_id(),orderRequest.getPortfolio_id());
    }


//    @DeleteMapping(path = "{orderId}")
//    public void deleteOrder(@PathVariable("orderId") Integer orderId) {
//        orderService.deleteOrder(orderId);
//
//        report.setTitle("client connectivity: Order");
//        report.setMsg("Order deleted.Order ID "+ orderId);
//        template.convertAndSend(topic.getTopic(), report);
//    }

    @PutMapping(path = "{orderId}")
    public void updateOrder(
            @PathVariable("orderId") Integer orderId,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) int quantity,
            @RequestParam(required = false) String orderStatus
    ) throws JsonProcessingException {
        orderService.updateOrder(orderId, price, quantity, orderStatus);
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString("Order Updated:  "+orderId.toString()));
        String report = "order with id "+orderId +" updated registered";
        template.convertAndSend(topic.getTopic(), report);
    }

    @GetMapping("get_user_orders/{userId}")
    public Set<Order> getUserOrders(@PathVariable(required = false) int userId) {
        User user = userService.findUserById(userId);
        return user.getOrders();
    }
}
