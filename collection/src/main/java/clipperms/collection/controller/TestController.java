package clipperms.collection.controller;

import clipperms.collection.messaging.CollectionMessageProducer;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class TestController {

    private final CollectionMessageProducer collectionMessageProducer;

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Service is up and running!");
    }

    @GetMapping("/kafka")
    public ResponseEntity<String> kafkaTest() {
        String sampleMessage = "Hello from the collection module!";
        collectionMessageProducer.sendMessage(sampleMessage);
        return ResponseEntity.ok().body(sampleMessage);
    }
}

