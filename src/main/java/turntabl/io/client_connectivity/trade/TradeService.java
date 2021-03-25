package turntabl.io.client_connectivity.trade;

import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class TradeService {
    private final TradeRepository tradeRepository;

    @Autowired

    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    public List<Trade> getTrades() {return tradeRepository.findAll(); }

    public void addNewTrade(Trade trade) {
        tradeRepository.save(trade);
    }

    @Transactional
    public void updateTrade(String tradeId, String order_status) {
        Trade trade =  tradeRepository.findTradeByOrderId(tradeId)
                .orElseThrow(() -> new IllegalStateException(
                        "Trade with order_id "+ tradeId + " does not exist"
                ));

        if (order_status != null && order_status.length() > 0)  {
            Optional<Trade> TradeOptional = tradeRepository.findTradeByOrderId(trade.getStatus());
            if (TradeOptional.isPresent()) {
                throw new IllegalStateException("ticker taken");
            }
            trade.setStatus(order_status);
        }
    }

}
