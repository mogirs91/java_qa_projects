package utils.random;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomUtils {

    private static final Integer length = 10;
    private static final Boolean useLetters = true;
    private static final Boolean useNumbers = false;

    public static String generateRandomText() {
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
