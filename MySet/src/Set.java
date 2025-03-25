import javax.naming.SizeLimitExceededException;
import java.util.NoSuchElementException;

public class Set <T> {
    private T[] set;
    private int size;
    private final int capacity;

    /***
     * Constructor
     * @param capacity maximum number of elements in the set
     */
    public Set(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.set = (T[]) new Object[capacity];

    }

    /***
     * Adds an element to the set
     * @param element element to add
     * @throws SizeLimitExceededException if the set is full
     */
    public void addElement (T element) throws SizeLimitExceededException {
        if (size == capacity) {
            throw new SizeLimitExceededException("Set is full");
        }

        for (int i = 0; i < size; i++) {
            if (set[i].equals(element)) {
                return;
            }
        }

        set[size] = element;
        size++;
    }

    /***
     * Removes an element from the set
     * @param element element to remove
     * @throws NoSuchElementException if the element is not found
     */
    public void removeElement(T element) {
        int index = seek(element);

        for (int i = index; i < size - 1; i++) {
            set[i] = set[i + 1];
        }

        size--;
    }

    /***
     * Adds elements from another set to the current set (creates union)
     * @param otherSet set to add elements from
     * @return new set with elements from both sets
     * @throws SizeLimitExceededException if the new set would exceed the capacity
     */
    public Set<T> addElements(Set<T> otherSet) throws SizeLimitExceededException {
        Set<T> tempSet = new Set<>(capacity+otherSet.capacity);

        for (int i = 0; i < size; i++) {
            tempSet.addElement(set[i]);
        }

        for (int i = 0; i < otherSet.size; i++) {
            tempSet.addElement(otherSet.set[i]);
        }

        return tempSet;
    }

    /***
     * Substracts elements from another set from the current set
     * @param otherSet set to substract elements from
     * @throws SizeLimitExceededException if the new set would exceed the capacity
     */
    public void substractElements(Set<T> otherSet) throws SizeLimitExceededException {
        Set<T> tempSet = new Set<>(capacity);
        for (int i = 0; i < size; i++) {
            boolean found = false;
            for (int j = 0; j < otherSet.size; j++) {
                if (set[i].equals(otherSet.set[j])) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                tempSet.addElement(set[i]);
            }
        }

        set = tempSet.set;
        size = tempSet.size;
    }

    /***
     * Intersects elements from another set with the current set
     * @param otherSet set to intersect elements with
     * @throws SizeLimitExceededException if the new set would exceed the capacity
     */
    public void intersectElements(Set<T> otherSet) throws SizeLimitExceededException {
        Set<T> tempSet = new Set<>(capacity);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < otherSet.size; j++) {
                if (set[i].equals(otherSet.set[j])) {
                    tempSet.addElement(set[i]);
                }
            }
        }

        set = tempSet.set;
        size = tempSet.size;
    }

    /***
     * Searches for an element in the set
     * @param element element to search for
     * @return index of the element
     * @throws NoSuchElementException if the element is not found
     */
    public int seek(T element) throws NoSuchElementException {
        for (int i = 0; i < size; i++) {
            if (set[i].equals(element)) {
                return i;
            }
        }

        throw new NoSuchElementException("Element not found");
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return capacity;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(set[i]).append(" ");
        }
        return result.toString();
    }
}
