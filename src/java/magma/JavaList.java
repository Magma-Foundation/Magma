package magma;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

record JavaList<T>(List<T> inner) implements Main.List_<T> {
    public JavaList() {
        this(new ArrayList<>());
    }

    @Override
    public Main.List_<T> add(T element) {
        List<T> copy = new ArrayList<>(inner);
        copy.add(element);
        return new JavaList<>(copy);
    }

    @Override
    public Main.List_<T> addAll(Main.List_<T> elements) {
        return elements.stream().<Main.List_<T>>foldWithInitial(this, Main.List_::add);
    }

    @Override
    public Main.Stream_<T> stream() {
        return streamWithIndices().map(Main.Tuple::right);
    }

    @Override
    public T popFirst() {
        return inner.removeFirst();
    }

    @Override
    public boolean hasElements() {
        return !inner.isEmpty();
    }

    @Override
    public int size() {
        return inner.size();
    }

    @Override
    public T last() {
        return inner.getLast();
    }

    @Override
    public Main.Stream_<Main.Tuple<Integer, T>> streamWithIndices() {
        return new Main.HeadedStream<>(new Main.RangeHead(inner.size())).map(index -> new Main.Tuple<>(index, inner.get(index)));
    }

    @Override
    public T first() {
        return inner.getFirst();
    }

    @Override
    public Main.List_<T> sort(Comparator<T> comparator) {
        ArrayList<T> copy = new ArrayList<>(inner);
        copy.sort(comparator);
        return new JavaList<>(copy);
    }

    @Override
    public Main.Option<T> apply(int index) {
        if (index < 0 || index >= size()) return new Main.None<>();
        return new Main.Some<>(inner.get(index));
    }
}
