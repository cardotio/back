package cardio.cardio;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import cardio.cardio.repository.TeamRepository;
import cardio.cardio.util.RandomCodeGenerator;

public class RandomCodeTest {
    List<String> codes = new ArrayList<>();
    @Test
	void testTeamCodeDuplication() {
        
            String randCode = RandomCodeGenerator.getRandomCode(6);

        System.out.print(randCode);
        
		
	}

    @Test
    void 널인값들추가() {

    }
    
}
