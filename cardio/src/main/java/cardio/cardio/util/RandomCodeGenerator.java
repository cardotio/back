package cardio.cardio.util;

import java.util.Random;

public class RandomCodeGenerator {
    
    static private String[] characters = "ABCDEFGHJKLMNPQRSTUVXYZabcdefghijkmnopqrstuvxyz23456789".split("");
    public static String getRandomCode(int length) {
        StringBuffer sb = new StringBuffer();
        Random rn = new Random();
        for( int i = 0 ; i < length ; i++ ) {
            sb.append( characters[ rn.nextInt( characters.length ) ] );
        }
        return sb.toString();
    }
}
