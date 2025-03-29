package magma.collect.list;class package magma.collect.list;{package magma.collect.list;

import magma.collect.stream.Stream;class import magma.collect.stream.Stream;{

import magma.collect.stream.Stream;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;

public interface List_<T> {
    Stream<T> stream();

    List_<T> add(T element);

    List_<T> addAll(List_<T> others);

    Option<T> findFirst();

    int size();

    List_<T> subList(int start, int end);

    boolean equalsTo(List_<T> other);
}
class public interface List_<T> {
    Stream<T> stream();

    List_<T> add(T element);

    List_<T> addAll(List_<T> others);

    Option<T> findFirst();

    int size();

    List_<T> subList(int start, int end);

    boolean equalsTo(List_<T> other);
}{

public interface List_<T> {
    Stream<T> stream();

    List_<T> add(T element);

    List_<T> addAll(List_<T> others);

    Option<T> findFirst();

    int size();

    List_<T> subList(int start, int end);

    boolean equalsTo(List_<T> other);
}
