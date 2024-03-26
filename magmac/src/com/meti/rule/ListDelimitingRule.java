package com.meti.rule;

public class ListDelimitingRule extends ListRule {
    private final Rule delimiter;

    public ListDelimitingRule(Rule value, Rule delimiter) {
        super(value);
        this.delimiter = delimiter;
    }

    @Override
    protected Rule createAnd(LazyRule lazy) {
        return AndRule.And(value, delimiter, lazy);
    }
}