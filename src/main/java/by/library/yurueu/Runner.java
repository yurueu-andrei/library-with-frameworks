package by.library.yurueu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@Profile("!test")
public class Runner {
    public static void main(String[] args) {
        SpringApplication.run(Runner.class, args);
    }
}