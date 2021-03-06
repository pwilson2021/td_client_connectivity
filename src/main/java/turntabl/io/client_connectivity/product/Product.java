package turntabl.io.client_connectivity.product;

import turntabl.io.client_connectivity.order.Order;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(
        name="Product",
        uniqueConstraints = {
                @UniqueConstraint(name = "ticker", columnNames = "ticker")
        })
public class Product {
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    private Integer id;

    @Column(
            name="ticker",
            nullable = false
    )
    private String ticker;

    @OneToOne(mappedBy = "product")
    private Order order;

    public Product( String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker =  ticker;
    }
}
