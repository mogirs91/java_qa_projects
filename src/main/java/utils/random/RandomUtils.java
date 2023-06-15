package utils.random;

import org.passay.CharacterRule;
import org.passay.CyrillicCharacterData;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {

    private static final Random rand = new SecureRandom();

    public static String getPassword(String email, Integer passlength) {
        CharacterRule LCRС = new CharacterRule(CyrillicCharacterData.LowerCase);
        LCRС.setNumberOfCharacters(1);
        CharacterRule UCR = new CharacterRule(EnglishCharacterData.UpperCase);
        UCR.setNumberOfCharacters(1);
        CharacterRule DR = new CharacterRule(EnglishCharacterData.Digit);
        DR.setNumberOfCharacters(1);
        PasswordGenerator passGen = new PasswordGenerator();
        String password = passGen.generatePassword(passlength - 1, UCR, DR, LCRС);
        return password + email.toCharArray()[rand.nextInt(email.toCharArray().length)];
    }
}

