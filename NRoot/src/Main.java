import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        System.out.println("Enter the number:");
        var number = scanner.nextInt();
        System.out.println("Enter the root:");
        var root = scanner.nextInt();

        try {
            var result = getNRoot(number, root);
            System.out.println("The result is: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }

    private static double getNRoot(int number, int root) {
        if(root<1){
            throw new IllegalArgumentException("Root must be greater than 0");
        }

        return Math.pow(number, 1.0 / root);
    }
}