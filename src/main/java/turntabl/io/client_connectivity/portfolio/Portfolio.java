package turntabl.io.client_connectivity.portfolio;

import turntabl.io.client_connectivity.order.Order;
import turntabl.io.client_connectivity.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "Portfolio")
@Table(name = "Portfolios")
public class Portfolio {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private int id;
    private String name;

    @OneToMany(mappedBy = "portfolio")
    private Set <Order> orders;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Portfolio(String ticker) {
        this.name = ticker;
    }

    public void setName(String name) {
        this.name = name;
    }

}
