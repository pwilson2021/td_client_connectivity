package turntabl.io.client_connectivity.portfolio;

import turntabl.io.client_connectivity.order.Order;
import turntabl.io.client_connectivity.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Portfolio {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int id;
    private String ticker;

    @OneToMany(mappedBy = "portfolio")
    private Set<Order> orders;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Portfolio(String ticker) {
        this.ticker = ticker;
    }
}
