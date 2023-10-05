package clipperms.trading.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok().body("Service is up and running!");
    }
}
