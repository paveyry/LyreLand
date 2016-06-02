package analysis;

import java.util.ArrayList;

/**
 * Created by olivier on 23/05/16.
 */
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

