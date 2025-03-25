import java.util.EmptyStackException;

public class Main {
    public static void main(String[] args) {
        var stos = new Stos<String>();

        System.out.println(stos.size());

        try {
            stos.pop();
        } catch (EmptyStackException e) {
            System.out.println("Unable to pop from an empty stack");
        }

        try {
            stos.peek();
        } catch (EmptyStackException e) {
            System.out.println("Unable to peek from an empty stack");
        }

        stos.push("A");
        stos.push("B");
        stos.push("C");

        System.out.println(stos);
        System.out.println(stos.size());
        System.out.println(stos.peek());
        System.out.println(stos);
        System.out.println(stos.pop());
        System.out.println(stos);
        System.out.println(stos.size());

    }
}