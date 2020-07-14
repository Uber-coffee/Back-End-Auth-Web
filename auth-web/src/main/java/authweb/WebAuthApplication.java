package authweb;

import authweb.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class WebAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAuthApplication.class, args);
    }
}
