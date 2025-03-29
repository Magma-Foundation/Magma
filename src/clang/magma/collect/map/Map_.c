package magma.collect.map;class package magma.collect.map;{package magma.collect.map;

import magma.collect.stream.Stream;class import magma.collect.stream.Stream;{

import magma.collect.stream.Stream;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.option.Tuple;class import magma.option.Tuple;{
import magma.option.Tuple;

public interface Map_<K, V> {
    Map_<K, V> with(K propertyKey, V propertyValue);

    Option<V> find(K propertyKey);

    Stream<Tuple<K, V>> stream();
}
class public interface Map_<K, V> {
    Map_<K, V> with(K propertyKey, V propertyValue);

    Option<V> find(K propertyKey);

    Stream<Tuple<K, V>> stream();
}{

public interface Map_<K, V> {
    Map_<K, V> with(K propertyKey, V propertyValue);

    Option<V> find(K propertyKey);

    Stream<Tuple<K, V>> stream();
}
