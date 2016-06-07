package analysis.containers;

import java.util.ArrayList;

public class CircularArrayList<E> extends ArrayList<E> {

    public E get(int index) {
        while (index < 0)
            index += size();
        index %= size();
        return super.get(index);
    }
}