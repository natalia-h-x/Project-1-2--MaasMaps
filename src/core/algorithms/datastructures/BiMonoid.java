package core.algorithms.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.IntFunction;

import lombok.Data;

/**
 * Monoid design pattern representing a backtrace of operations so we can see
 * which operations were used to reach a certain state.
 */
@Data
public class BiMonoid<T, X, S> {
    /** Backtrace of how we got to the final element */
    private List<T> elements;

    /** Generator of the backtrace */
    private BiFunction<S, X, T> extractor;

    public BiMonoid(BiFunction<S, X, T> extractor) {
        this.extractor = extractor;
        elements = new ArrayList<>();
    }

    /**
     * Copy constructor of another monoid, it keeps a reference to the extractor function
     * but copies the elements.
     *
     * @param monoid The monoid to copy.
     */
    public BiMonoid(BiMonoid<T, X, S> monoid) {
        this.extractor = monoid.getExtractor();
        elements = new ArrayList<>(monoid.getElements());
    }

    public void add(S elementShell, X additional) {
        elements.add(extractor.apply(elementShell, additional));
    }

    public Object[] toArray() {
        return elements.toArray();
    }

    public T[] toArray(T[] a) {
        return elements.toArray(a);
    }

    public T[] toArray(IntFunction<T[]> generator) {
        return elements.toArray(generator);
    }
}
