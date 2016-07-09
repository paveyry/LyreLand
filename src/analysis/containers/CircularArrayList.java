package analysis.containers;

import java.util.ArrayList;

/**
 * Wrapper around an ArrayList to make it Circular (list.get(-1) returns the last element)
 */
public class CircularArrayList<E> extends ArrayList<E> {

    /**
     * Overriding of the get method
     * @param index Index for the access
     * @return Corresponding value
     */
    public E get(int index) {
        while (index < 0)
            index += size();
        index %= size();
        return super.get(index);
    }
}