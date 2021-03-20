package turntabl.io.client_connectivity.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {this.productService = productService; }

    @GetMapping
    public List<Product> getProducts() { return productService.getProducts(); }

    @PostMapping
    public void registerNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteStudent(@PathVariable("productId") Integer productId) {productService.deleteProducts(productId);}


    public void updateProduct(
            @PathVariable("productId") Integer productId,
            @RequestParam(required = false) String ticker
    ) {
        productService.updateProduct(productId, ticker);
    }
}
