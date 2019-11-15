package lvat.oauth2sociallogin;

import lvat.oauth2sociallogin.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = AppProperties.class)
public class Oauth2SocialLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(Oauth2SocialLoginApplication.class, args);
    }

}
