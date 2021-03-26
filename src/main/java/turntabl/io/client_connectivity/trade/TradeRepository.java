package turntabl.io.client_connectivity.trade;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TradeRepository extends JpaRepository<Trade, Integer> {
    Optional<Trade> findTradeByOrderId(String order_id);
}
