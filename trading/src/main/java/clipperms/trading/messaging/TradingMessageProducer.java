package clipperms.trading.messaging;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class TradingMessageProducer {

    private final KafkaTemplate<String, String> stringTemplate;

    public TradingMessageProducer(KafkaTemplate<String, String> stringTemplate) {
        this.stringTemplate = stringTemplate;
    }

    public void sendMessage(String message) {
        stringTemplate.send("trading-topic", message);
    }
}
