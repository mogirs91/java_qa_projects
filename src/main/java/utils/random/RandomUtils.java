package utils.random;

import org.passay.CharacterRule;
import org.passay.CyrillicCharacterData;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {

    private static final Random rand = new SecureRandom();
    private static final Integer passlength = 9;

    public static String getPassword(String email) {
        CharacterRule LCRC = new CharacterRule(CyrillicCharacterData.LowerCase);
        LCRC.setNumberOfCharacters(1);
        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        UCR.setNumberOfCharacters(1);
        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        DR.setNumberOfCharacters(1);
        PasswordGenerator passGen = new PasswordGenerator();
        String password = passGen.generatePassword(passlength, UCR, DR, LCRC);
        return password + email.toCharArray()[rand.nextInt(email.toCharArray().length)];
    }

    public static Integer getRandomInteger(int from, int to) {
        if (from < to)
            return from + new Random().nextInt(Math.abs(to - from));
        return from - new Random().nextInt(Math.abs(to - from));
    }
}
