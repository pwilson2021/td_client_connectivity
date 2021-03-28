package turntabl.io.client_connectivity.order;

public class OrderRequest {
    private double price;
    private int quantity;
    private String order_type;
    private String order_status;
    private int user_id;
    private int portfolio_id;
    private int product_id;

    public OrderRequest(double price, int quantity, String order_type, String order_status, int user_id, int portfolio_id, int product_id) {
        this.price = price;
        this.quantity = quantity;
        this.order_type = order_type;
        this.order_status = order_status;
        this.user_id = user_id;
        this.portfolio_id = portfolio_id;
        this.product_id = product_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPortfolio_id() {
        return portfolio_id;
    }

    public void setPortfolio_id(int portfolio_id) {
        this.portfolio_id = portfolio_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
