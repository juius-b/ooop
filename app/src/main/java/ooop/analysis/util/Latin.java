package ooop.analysis.util;

public class Latin {
    public static final int ALPHABET_LENGTH = 26;
    public static final int ALPHABET_POSITION_MASK = 0b11111;

    public static boolean isLetter(int codePoint) {
        return isLowercaseLetter(codePoint) || isUppercaseLetter(codePoint);
    }

    private static boolean isLowercaseLetter(int codePoint) {
        return codePoint >= 'a' && codePoint <= 'z';
    }
    private static boolean isUppercaseLetter(int codePoint) {
        return codePoint >= 'A' && codePoint <= 'Z';
    }
}
