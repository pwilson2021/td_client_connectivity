package turntabl.io.client_connectivity.trade;

import turntabl.io.client_connectivity.order.Order;

import javax.persistence.*;

@Entity
@Table(name="Trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double price;
    private double quantity;
    private String status;

    @ManyToOne
    private Order order;

    public Trade(int id, double price, double quantity, Order order, String status) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.order = order;
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}