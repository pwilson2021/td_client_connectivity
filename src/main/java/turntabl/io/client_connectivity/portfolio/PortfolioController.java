package turntabl.io.client_connectivity.portfolio;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.web.bind.annotation.*;
import turntabl.io.client_connectivity.order.Order;
import turntabl.io.client_connectivity.reporting.ReportingModel;
import turntabl.io.client_connectivity.user.User;
import turntabl.io.client_connectivity.user.UserRepository;
import turntabl.io.client_connectivity.user.UserService;
import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(path = "api/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;
    @Autowired
    private RedisTemplate template;
    @Autowired
    private ChannelTopic topic;

    private ReportingModel report;

    private final UserService userService;
    ObjectMapper mapper = new ObjectMapper();


    @Autowired
    public PortfolioController(PortfolioService portfolioService, UserService userService) {
        this.portfolioService = portfolioService;
        this.userService = userService;
    }

    @GetMapping
    public List<Portfolio> getPortfolio() { return portfolioService.getPortfolio(); }

    @PostMapping
    public void registerNewPortfolio(@RequestParam(name = "name") String portfolioName, @RequestParam(name= "user_id") Integer user_id) throws JsonProcessingException {
        User user = userService.findUserById(user_id);
        Portfolio portfolio = new Portfolio(portfolioName, user);
        portfolioService.addNewPortfolio(portfolio);

        String report = "new portfolio registered"+portfolio.toString();
        template.convertAndSend(topic.getTopic(), mapper.writeValueAsString(report));
        template.convertAndSend(topic.getTopic(), report);
    }

    @GetMapping(path = "{portfolioId}")
    public void fetchPortfolioInfo (@PathVariable("portfolioId") Integer portfolioId) {
        portfolioService.fetchStock(portfolioId);
    }

//    @DeleteMapping(path = "{portfolioId}")
//    public void deletePortfolio(@PathVariable("portfolioId") Integer portfolioId) {
//        portfolioService.deletePortfolio(portfolioId);
//        report.setTitle("client connectivity: product");
//        report.setMsg(" Portfolio deleted. Portfolio ID: "+ portfolioId);
//        template.convertAndSend(topic.getTopic(), report);
//    }

    @PutMapping(path = "{portfolioId}")
    public void updatePortfolio(
            @PathVariable("portfolioId") Integer portfolioId,
            @RequestParam(required = false) String name
    ) {
        portfolioService.updatePortfolio(name, portfolioId);
    }

    @GetMapping(path = {"userId"})
    public Set<Portfolio> getUserPortfolios(@RequestParam(required = false) int userId) {
        User user = userService.findUserById(userId);
        return user.getPortfolio();
    }
}
