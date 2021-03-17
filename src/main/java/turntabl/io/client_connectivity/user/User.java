package turntabl.io.client_connectivity.user;

import turntabl.io.client_connectivity.order.Order;
import turntabl.io.client_connectivity.portfolio.Portfolio;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "User")
@Table(
        name="Users",
        uniqueConstraints = {
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class User {
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")

    @Column(name = "id", updatable = false)
    private int id;

    @Column (name = "first_name", nullable = false, columnDefinition = "TEXT")
    private String first_name;

    @Column (name = "last_name", nullable = false, columnDefinition = "TEXT")
    private String last_name;

    @Column(name = "email", nullable = false, columnDefinition = "TEXT")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name= "funds", nullable = false)
    private double funds;

    @Column(name = "date_created", nullable = false)
    private LocalDate date_created;

    @Column(name = "is_admin", nullable = false)
    private boolean is_admin;



    public User(String first_name, String last_name, String password, double funds, LocalDate date_created, boolean is_admin, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.password = password;
        this.funds = funds;
        this.date_created = date_created;
        this.is_admin = is_admin;
        this.email = email;
    }


    public User() {
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public LocalDate getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDate date_created) {
        this.date_created = date_created;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @OneToMany(mappedBy = "user")
    private Set<Portfolio> portfolio;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", funds=" + funds +
                ", date_created=" + date_created +
                ", is_admin=" + is_admin +
                '}';
    }
}
