package english.chatbot.application.domain;

public enum Difficulty {
    EASY, NORMAL, HARD;

    public static Difficulty from (String value) {
        try {
            return Difficulty.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid difficulty value: " + value);
        }
    }
}
