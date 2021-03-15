package turntabl.io.client_connectivity.order;

import turntabl.io.client_connectivity.portfolio.Portfolio;
import turntabl.io.client_connectivity.user.User;

import javax.persistence.*;

@Entity(name= "Order")
@Table(name = "Orders")
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )

    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(
            name = "product_id",
            updatable = false,
            nullable = false
    )
    private int product_id;


    private double price;
    private int quantity;
    private String order_type;

    @Column(name = "date_created", updatable = false, nullable = false
    )
    private String date_created;
    private String order_status;


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", referencedColumnName = "id")
    private Portfolio portfolio;


    public Order(int product_id, double price, int quantity, String order_type, String date_created, String order_status) {
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
        this.order_type = order_type;
        this.date_created = date_created;
        this.order_status = order_status;
    }

    public Order(int product_id, double price, int quantity, String order_type, String date_created, String order_status, User user_id) {
        this.product_id = product_id;
        this.price = price;
        this.quantity = quantity;
        this.order_type = order_type;
        this.date_created = date_created;
        this.order_status = order_status;
        this.user = user_id;
    }

    public Order() {

    }
}