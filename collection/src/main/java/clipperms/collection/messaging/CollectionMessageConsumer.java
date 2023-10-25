package clipperms.collection.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CollectionMessageConsumer {
    @KafkaListener(topics = "trading-topic", groupId = "trading-group")
    public void listen(String message) {
        // Handle the incoming message & simply print to console for now -> simple test
        System.out.println("Received message in collection module: " + message);
        //save to db
    }
}
