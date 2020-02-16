package store.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class StoreItemServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreItemServiceApplication.class, args);
    }

}
