package clipperms.trading;

import clipperms.trading.messaging.TradingMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TradingModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingModuleApplication.class, args);
	}

}
