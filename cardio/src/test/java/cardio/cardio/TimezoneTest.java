package cardio.cardio;

import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.Test;

public class TimezoneTest {
    @Test
    public void queryFilterForActionLogBotTest() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        System.out.println("** 국제 표준 시간 **");
        System.out.println(new Date().toString());

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        System.out.println("** 한국 시간 **");
        System.out.println(new Date().toString());
        
        // LocalDateTime.now().toString()으로도 출력가능 - 출력형식이 다름

    }
}
