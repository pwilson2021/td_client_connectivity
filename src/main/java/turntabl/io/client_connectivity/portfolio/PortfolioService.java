package turntabl.io.client_connectivity.portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public PortfolioService(PortfolioRepository portfolioRepository) {this.portfolioRepository = portfolioRepository;}

    public List<Portfolio> getPortfolio() { return portfolioRepository.findAll(); }

    public void addNewPortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }

    public void deletePortfolio(int portfolioId) {
        boolean exists = portfolioRepository.existsById(portfolioId);
        if (!exists) {
            throw new IllegalStateException("Portfolio with id " + portfolioId + " doesn't exists");
        }
        portfolioRepository.existsById(portfolioId);
    }

    @Transactional
    public void updatePortfolio(String name, int portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalStateException(
                        "Portfolio with id " + portfolioId + " doesn't exists"
                ));
        if (name != null) {
            portfolio.setName(name);
        }
    }
}

