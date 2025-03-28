package magma.api.collect;

public interface Set_<T> {
    Stream<T> stream();

    Set_<T> add(T element);
}
