package magma.compile.rule;class package magma.compile.rule;{package magma.compile.rule;

import magma.option.None;class import magma.option.None;{

import magma.option.None;
import magma.option.Option;class import magma.option.Option;{
import magma.option.Option;
import magma.option.Some;class import magma.option.Some;{
import magma.option.Some;

public record OrState(Option<String> result) {
    public OrState() {
        this(new None<>());
    }

    public OrState withValue(String value) {
        return new OrState(new Some<>(value));
    }

    public Option<String> toOption() {
        return result;
    }
}
class public record OrState(Option<String> result) {
    public OrState() {
        this(new None<>());
    }

    public OrState withValue(String value) {
        return new OrState(new Some<>(value));
    }

    public Option<String> toOption() {
        return result;
    }
}{

public record OrState(Option<String> result) {
    public OrState() {
        this(new None<>());
    }

    public OrState withValue(String value) {
        return new OrState(new Some<>(value));
    }

    public Option<String> toOption() {
        return result;
    }
}
