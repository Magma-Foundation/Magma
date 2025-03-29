package magma.compile;class package magma.compile;{package magma.compile;

import magma.collect.list.List_;class import magma.collect.list.List_;{

import magma.collect.list.List_;
import magma.collect.stream.Stream;class import magma.collect.stream.Stream;{
import magma.collect.stream.Stream;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.option.Tuple;class import magma.option.Tuple;{
import magma.option.Tuple;

import java.util.function.Function;class import java.util.function.Function;{

import java.util.function.Function;

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);

    Node withNodeList(String propertyKey, List_<Node> propertyValues);

    Option<List_<Node>> findNodeList(String propertyKey);

    String display();

    Node mapNodeList(String propertyKey, Function<List_<Node>, List_<Node>> mapper);

    boolean is(String type);

    Node withType(String type);

    Node merge(Node other);

    Stream<Tuple<String, String>> streamStrings();

    Stream<Tuple<String, List_<Node>>> streamNodeLists();
}
class public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);

    Node withNodeList(String propertyKey, List_<Node> propertyValues);

    Option<List_<Node>> findNodeList(String propertyKey);

    String display();

    Node mapNodeList(String propertyKey, Function<List_<Node>, List_<Node>> mapper);

    boolean is(String type);

    Node withType(String type);

    Node merge(Node other);

    Stream<Tuple<String, String>> streamStrings();

    Stream<Tuple<String, List_<Node>>> streamNodeLists();
}{

public interface Node {
    Node withString(String propertyKey, String propertyValue);

    Option<String> findString(String propertyKey);

    Node withNodeList(String propertyKey, List_<Node> propertyValues);

    Option<List_<Node>> findNodeList(String propertyKey);

    String display();

    Node mapNodeList(String propertyKey, Function<List_<Node>, List_<Node>> mapper);

    boolean is(String type);

    Node withType(String type);

    Node merge(Node other);

    Stream<Tuple<String, String>> streamStrings();

    Stream<Tuple<String, List_<Node>>> streamNodeLists();
}
