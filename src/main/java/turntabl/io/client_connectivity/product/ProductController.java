package turntabl.io.client_connectivity.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.reporting.ReportingModel;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;

    private ReportingModel report;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public ProductController(ProductService productService, RedisTemplate redisTemplate) {this.productService = productService; this.template = redisTemplate; }

    @GetMapping
    public List<Product> getProducts() { return productService.getProducts(); }

    @PostMapping
    public void registerNewProduct(@RequestParam(name = "ticker") String ticker) throws JsonProcessingException {
        Product product = new Product(ticker);
//        System.out.println(product.toString());
        productService.addNewProduct(product);
        String report = "new product registered"+product.toString();
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString(report));
//        template.convertAndSend(topic.getTopic(), report);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteStudent(@PathVariable("productId") Integer productId)  throws JsonProcessingException {
        productService.deleteProducts(productId);
        String report = "new product registered "+ productId;
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString(report));
    }


    public void updateProduct(
            @PathVariable("productId") Integer productId,
            @RequestParam(required = false) String ticker
    ) throws JsonProcessingException {
        productService.updateProduct(productId, ticker);
        String report = "new product registered "+ productId;
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString(report));
    }
}
