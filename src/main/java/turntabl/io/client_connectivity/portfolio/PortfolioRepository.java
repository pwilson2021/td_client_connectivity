package turntabl.io.client_connectivity.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
//    Optional<Object> findAllById(int portfolioId);
}
