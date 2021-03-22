package turntabl.io.client_connectivity.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.reporting.ReportingModel;

import java.util.List;

@RestController
@RequestMapping(path = "api/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;
    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;

    private ReportingModel report;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {this.portfolioService = portfolioService; }

    @GetMapping
    public List<Portfolio> getPortfolio() { return portfolioService.getPortfolio(); }

    @PostMapping
    public void registerNewPortfolio(@RequestBody Portfolio portfolio) {
        portfolioService.addNewPortfolio(portfolio);
        report.setTitle("client connectivity: portfolio");
        report.setMsg("New portfolio created");
        template.convertAndSend(topic.getTopic(), report);
    }

    @DeleteMapping(path = "{portfolioId}")
    public void deletePortfolio(@PathVariable("portfolioId") Integer portfolioId) {
        portfolioService.deletePortfolio(portfolioId);
        report.setTitle("client connectivity: product");
        report.setMsg(" Portfolio deleted. Portfolio ID: "+ portfolioId);
        template.convertAndSend(topic.getTopic(), report);
    }

    public void updatePortfolio(
            @PathVariable("portfolioId") Integer portfolioId,
            @RequestParam(required = false) String name
    ) { portfolioService.updatePortfolio(name, portfolioId);}
}
