import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Enter number for square root counting: ");
        double number = scanner.nextDouble();

        try {
            double result = squareRoot(number);
            System.out.println("Square root of " + number + " is " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static double squareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number must be positive");
        }

        return Math.sqrt(number);
    }
}