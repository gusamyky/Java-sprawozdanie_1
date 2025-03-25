import java.util.EmptyStackException;
import java.util.LinkedList;

public class Stos<T> {
    private final LinkedList<T> list = new LinkedList<>();

    public void push(T value) {
        list.addFirst(value);
    }

    public T pop() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.removeFirst();
    }

    public int size() {
        return list.size();
    }

    public T peek() {
        if (list.isEmpty()) {
            throw new EmptyStackException();
        }
        return list.getFirst();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
