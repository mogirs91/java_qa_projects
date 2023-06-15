package utils.data;

public class CommonUtils {

    public static String cleanStringForFilePath(final String dirty) {
        String clean = dirty;
        if (clean != null) {
            clean = clean.replaceAll("\\s+", " ");
            clean = clean.replaceAll("[^a-zA-Z0-9_ ]", "");
            clean = clean.trim();
            clean = clean.toLowerCase();
            clean = clean.replaceAll("\\s+", "_");
            return clean;
        }
        return null;
    }
}
