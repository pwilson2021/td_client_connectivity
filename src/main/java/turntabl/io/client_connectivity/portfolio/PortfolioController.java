package turntabl.io.client_connectivity.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {this.portfolioService = portfolioService; }

    @GetMapping
    public List<Portfolio> getPortfolio() { return portfolioService.getPortfolio(); }

    @PostMapping
    public void registerNewPortfolio(@RequestBody Portfolio portfolio) { portfolioService.addNewPortfolio(portfolio); }

    @DeleteMapping(path = "{portfolioId}")
    public void deletePortfolio(@PathVariable("portfolioId") Integer portfolioId) { portfolioService.deletePortfolio(portfolioId); }

    public void updatePortfolio(
            @PathVariable("portfolioId") Integer portfolioId,
            @RequestParam(required = false) String name
    ) { portfolioService.updatePortfolio(name, portfolioId);}
}
