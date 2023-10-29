package clipperms.collection.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageProducer{

    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String message){
        log.info("Sending message... " + message);
        rabbitTemplate.convertAndSend(RabbitConfig.TOPIC_EXCHANGE_NAME, "coll.test.first", message);
    }

}
