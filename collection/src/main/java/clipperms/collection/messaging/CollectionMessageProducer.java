package clipperms.collection.messaging;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollectionMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public CollectionMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("collection-topic", message);
        System.out.println("Sent message to collection-topic: " + message);
    }
}
