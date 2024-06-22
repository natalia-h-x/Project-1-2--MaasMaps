package core.datastructures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

import lombok.Data;

/**
 * Monoid design pattern representing a backtrace of operations so we can see
 * which operations were used to reach a certain state.
 */
@Data
public class Monoid<T, S> {
    /** Backtrace of how we got to the final element */
    private List<T> elements;

    /** Generator of the backtrace */
    private Function<S, T> extractor;

    public Monoid(Function<S, T> extractor) {
        this.extractor = extractor;
        elements = new ArrayList<>();
    }

    /**
     * Copy constructor of another monoid, it keeps a reference to the extractor function
     * but copies the elements.
     *
     * @param monoid The monoid to copy.
     */
    public Monoid(Monoid<T, S> monoid) {
        this.extractor = monoid.getExtractor();
        elements = new ArrayList<>(monoid.getElements());
    }

    public void add(S elementShell) {
        elements.add(extractor.apply(elementShell));
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
