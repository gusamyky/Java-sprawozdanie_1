import javax.naming.SizeLimitExceededException;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        Set<Integer> set1 = new Set<>(5);
        Set<Integer> set2 = new Set<>(5);

        try {
            set1.addElement(1);
            set1.addElement(2);
            set1.addElement(3);
            set1.addElement(4);
            set1.addElement(5);

            set2.addElement(4);
            set2.addElement(5);
            set2.addElement(6);
            set2.addElement(7);
            set2.addElement(8);

            // Union, substraction and intersection demo:
            System.out.println("Set 1: " + set1);
            System.out.println("Set 2: " + set2);

            Set<Integer> union = set1.addElements(set2);
            System.out.println("Union: " + union);

            set1.substractElements(set2);
            System.out.println("Set 1 - Set 2: " + set1);

            union.intersectElements(set2);
            System.out.println("Union ∩ Set 2: " + union);

            // Add and remove demo:
            set1.addElement(4);
            set1.addElement(4);

            System.out.println("Set 1 after adding '4' twice: " + set1);

            set1.removeElement(4);
            System.out.println("Set 1 after removing '4': " + set1);

            // this will throw an exception
            set2.removeElement(192); // NoSuchElementException
//            set1.seek(9); // NoSuchElementException
//            set2.addElement(9); // SizeLimitExceededException


        } catch (NoSuchElementException | SizeLimitExceededException e) {
            System.out.println(e.getMessage());
        }
    }
}