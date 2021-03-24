package turntabl.io.client_connectivity.portfolio;


import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Portfolio() {}

    public Portfolio(String name) {
        this.name = name;
    }

    public Portfolio(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public User getUser() {
        return user;
    }
}
