package turntabl.io.client_connectivity.portfolio;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.user.User;
import turntabl.io.client_connectivity.user.UserRepository;
import turntabl.io.client_connectivity.user.UserService;
import java.util.List;

@RestController
@RequestMapping(path = "api/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final UserService userService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService, UserService userService) {
        this.portfolioService = portfolioService;
        this.userService = userService;
    }

    @GetMapping
    public List<Portfolio> getPortfolio() { return portfolioService.getPortfolio(); }

    @PostMapping
    public void registerNewPortfolio(@RequestParam(name = "name") String portfolioName, @RequestParam(name= "user_id") Integer user_id) {
        User user = userService.findUserById(user_id);
        Portfolio portfolio = new Portfolio(portfolioName, user);
        portfolioService.addNewPortfolio(portfolio);
    }

    @GetMapping(path = "{portfolioId}")
    public void fetchPortfolioInfo (@PathVariable("portfolioId") Integer portfolioId) {
        portfolioService.fetchStock(portfolioId);
    }

    @DeleteMapping(path = "{portfolioId}")
    public void deletePortfolio(@PathVariable("portfolioId") Integer portfolioId) { portfolioService.deletePortfolio(portfolioId); }

    public void updatePortfolio(
            @PathVariable("portfolioId") Integer portfolioId,
            @RequestParam(required = false) String name
    ) { portfolioService.updatePortfolio(name, portfolioId);}
}
