package turntabl.io.client_connectivity.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.reporting.ReportingModel;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;
    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;

    private ReportingModel report;

    @Autowired
    public ProductController(ProductService productService) {this.productService = productService; }

    @GetMapping
    public List<Product> getProducts() { return productService.getProducts(); }

    @PostMapping
    public void registerNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
        report.setTitle("client connectivity: product");
        report.setMsg("New product registered");
        template.convertAndSend(topic.getTopic(), report);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteStudent(@PathVariable("productId") Integer productId) {
        productService.deleteProducts(productId);
        report.setTitle("client connectivity: product");
        report.setMsg(" Product deleted. product ID: "+productId);
        template.convertAndSend(topic.getTopic(), report);
    }


    public void updateProduct(
            @PathVariable("productId") Integer productId,
            @RequestParam(required = false) String ticker
    ) {
        productService.updateProduct(productId, ticker);
        report.setTitle("client connectivity: product");
        report.setMsg("Product updated: product ID: "+ productId+ " "+ ticker);
        template.convertAndSend(topic.getTopic(), report);
    }
}
