package magma.collect;class package magma.collect;{package magma.collect;

import magma.option.None;class import magma.option.None;{

import magma.option.None;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.option.Some;class import magma.option.Some;{
import magma.option.Some;

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    @Override
    public Option<String> createInitial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> maybeCurrent, String element) {
        return new Some<>(maybeCurrent.map(inner -> inner + delimiter + element).orElse(element));
    }
}
class public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    @Override
    public Option<String> createInitial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> maybeCurrent, String element) {
        return new Some<>(maybeCurrent.map(inner -> inner + delimiter + element).orElse(element));
    }
}{

public record Joiner(String delimiter) implements Collector<String, Option<String>> {
    @Override
    public Option<String> createInitial() {
        return new None<>();
    }

    @Override
    public Option<String> fold(Option<String> maybeCurrent, String element) {
        return new Some<>(maybeCurrent.map(inner -> inner + delimiter + element).orElse(element));
    }
}
