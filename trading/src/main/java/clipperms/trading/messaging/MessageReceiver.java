package clipperms.trading.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Component
public class MessageReceiver {

    private CountDownLatch latch = new CountDownLatch(1);
    private final AtomicReference<String> latestMessage = new AtomicReference<>();

    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        latestMessage.set(message);  // Storing latest message
        latch.countDown();
    }

    public String getLatestMessage() {
        return latestMessage.get();  // Fetching the latest message
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
