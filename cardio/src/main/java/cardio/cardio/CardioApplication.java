package cardio.cardio;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardioApplication {
	@PostConstruct
    public void started() {
      // timezone UTC 셋팅
      TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
	public static void main(String[] args) {
		SpringApplication.run(CardioApplication.class, args);
	}

}
