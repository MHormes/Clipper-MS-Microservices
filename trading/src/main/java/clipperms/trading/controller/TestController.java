package clipperms.trading.controller;

import clipperms.trading.messaging.TradingMessageConsumer;
import clipperms.trading.messaging.TradingMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final TradingMessageConsumer tradingMessageConsumer;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Service is up and running!");
    }

    @GetMapping("/kafka")
    public ResponseEntity<String> kafkaTest() {
        String message = tradingMessageConsumer.getLatestMessage();
        return ResponseEntity.ok().body(message);
    }
}
