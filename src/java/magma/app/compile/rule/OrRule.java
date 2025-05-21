package magma.app.compile.rule;

import magma.api.collect.list.Iterable;

public record OrRule<T>(Iterable<Rule<T>> rules) {
}