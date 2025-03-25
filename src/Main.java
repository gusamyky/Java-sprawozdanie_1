public class Main {
    public static void main(String[] args) {
        try {
            checkValue(1001);
        } catch (ValueTooLargeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkValue(int value) {
        int limit = 1000;
        if (value > limit) {
            throw new ValueTooLargeException("Value " + value + " exceeds the limit of " + limit);
        }
    }
}