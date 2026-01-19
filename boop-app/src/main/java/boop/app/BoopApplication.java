package boop.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "boop")
public class BoopApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoopApplication.class, args);
    }
}
