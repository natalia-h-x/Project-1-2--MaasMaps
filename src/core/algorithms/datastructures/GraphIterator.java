package core.algorithms.datastructures;

import java.util.Iterator;
import java.util.Set;

public class GraphIterator<T> implements Iterator<T>{
    T[] iterateArray;
    int currentIndex = 0;
    int i = -1;

    public GraphIterator(Set<T> graph) {
        int j = 0;
        iterateArray = (T[]) new Object[graph.size()];

        for (T element : graph) {
            iterateArray[j] = element;
            j++;
        }
    }

    @Override
    public boolean hasNext() {
        if (i+1 >= iterateArray.length) 
            return false;
        return true;
    }

    @Override
    public T next() {
        i++;
        return iterateArray[i];
    }
}
