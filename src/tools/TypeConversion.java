package tools;

import java.util.List;

public class TypeConversion {
    public static int[] convertIntegers(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; ++i)
        {
            ret[i] = integers.get(i);
        }
        return ret;
    }
}
