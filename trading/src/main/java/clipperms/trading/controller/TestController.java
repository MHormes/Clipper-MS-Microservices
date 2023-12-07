package clipperms.trading.controller;

import clipperms.trading.messaging.MessageReceiver;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final MessageReceiver messageReceiver;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Service is up and running!");
    }

    @GetMapping("/message")
    public ResponseEntity<String> rabbitMQTest(){
        String message = messageReceiver.getLatestMessage();
        return ResponseEntity.ok().body(message);
    }
}
