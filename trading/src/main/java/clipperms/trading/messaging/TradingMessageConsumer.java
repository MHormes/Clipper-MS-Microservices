package clipperms.trading.messaging;

import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@Getter
@Component
public class TradingMessageConsumer {
    // Method to retrieve the most recent message
    private String latestMessage;

    // Consume messages from the "custom-topic"
    @KafkaListener(id = "collectionListener", topics = "collection-topic")
    public void consumeCollectionTopicMessage(String message) {
        // Handle messages from the "custom-topic"
        System.out.println("Received message from collection-topic: " + message);
        latestMessage = message;
    }
}
