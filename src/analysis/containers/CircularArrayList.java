package analysis.containers;

import java.util.ArrayList;

public class CircularArrayList<E> extends ArrayList<E> {

    public E get(int index) {
        if (index < 0)
            index = size()-index;
        if (index > 0)
            index = 0 + index;
        else if (index == size())
            index = 0;
            return super.get(index);
    }
}

